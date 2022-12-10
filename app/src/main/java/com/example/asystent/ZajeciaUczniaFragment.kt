package com.example.asystent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.asystent.databinding.FragmentZajeciaUczniaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ZajeciaUczniaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZajeciaUczniaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentZajeciaUczniaBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var przypisaneZajecia : MutableList<ZajeciaUcznia>
    private lateinit var uczniowieList : MutableList<Uczniowie>
    private lateinit var zajeciaList : MutableList<Zajecia>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onResume() {
        super.onResume()
        val nrAlbumuArray : ArrayList<String> = arrayListOf()
        uczniowieList.forEach{ nrAlbumuArray.add("${it.nrAlbumu} - ${it.imieNazwisko}") }
        val nrAlbumuArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, nrAlbumuArray)
        binding.editNrAlbumuPrzypisanieWartosc.setAdapter(nrAlbumuArrayAdapter)

        val nazwaZajecArray : ArrayList<String> = arrayListOf()
        zajeciaList.forEach { nazwaZajecArray.add(it.nazwaZajec) }
        val nazwaZajecArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, nazwaZajecArray)
        binding.editNazwaZajecPrzypisanieWartosc.setAdapter(nazwaZajecArrayAdapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentZajeciaUczniaBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        binding.buttonPrzypisz.setOnClickListener {
            writeData()
        }
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            przypisaneZajecia = appDatabase.zajeciaUczniaDao().getAll()
            zajeciaList = appDatabase.zajeciaDao().getAll()
            uczniowieList = appDatabase.uczniowieDao().getAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun writeData() {
        val nazwaZajec = binding.editNazwaZajecPrzypisanieWartosc.text.toString()
        val nrAlbumu = binding.editNrAlbumuPrzypisanieWartosc.text.toString().split(' ')[0]

        if (nazwaZajec.isEmpty() || nrAlbumu.isEmpty()) {
            Toast.makeText(activity, "Please enter all data", Toast.LENGTH_SHORT).show()
        } else if (przypisaneZajecia.find{ it == ZajeciaUcznia(nazwaZajec, nrAlbumu.toInt())} != null) {
            Toast.makeText(activity, "This connection already exists", Toast.LENGTH_SHORT).show()
        } else {
            val zajeciaUcznia = ZajeciaUcznia(nazwaZajec, nrAlbumu.toInt())
            GlobalScope.launch(Dispatchers.IO) {
                appDatabase.zajeciaUczniaDao().insert(zajeciaUcznia)
                przypisaneZajecia.add(zajeciaUcznia)
            }
            binding.editNazwaZajecPrzypisanieWartosc.text.clear()
            binding.editNrAlbumuPrzypisanieWartosc.text.clear()

            Toast.makeText(activity, "Successfully written", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ZajeciaUczniaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ZajeciaUczniaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}