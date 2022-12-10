package com.example.asystent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.asystent.databinding.FragmentDodajUczniaBinding
import com.example.asystent.databinding.FragmentDodajZajeciaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DodajUczniaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DodajUczniaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDodajUczniaBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var uczniowieList : MutableList<Uczniowie>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDodajUczniaBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        binding.buttonDodajUcznia.setOnClickListener {
            writeData()
        }
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            uczniowieList = appDatabase.uczniowieDao().getAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun writeData() {
        val imieNazwisko = binding.editImieNazwisko.text.toString()
        val nrAlbumu = binding.editNrAlbumu.text.toString()
        val albumRegex = Regex("^[0-9]{6}\$")

        if (!nrAlbumu.matches(albumRegex)) {
            Toast.makeText(activity, "Please enter valid album number", Toast.LENGTH_SHORT).show()
        } else if (imieNazwisko.isEmpty() || nrAlbumu.isEmpty()) {
        Toast.makeText(activity, "Please enter all data", Toast.LENGTH_SHORT).show()
        } else if (uczniowieList.find{ it.nrAlbumu == nrAlbumu.toInt()} != null) {
            Toast.makeText(activity, "This person already exists in database", Toast.LENGTH_SHORT).show()
        } else {
            val uczen = Uczniowie(imieNazwisko, nrAlbumu.toInt())
            GlobalScope.launch(Dispatchers.IO) {
                appDatabase.uczniowieDao().insert(uczen)
                uczniowieList.add(uczen)
            }
            binding.editImieNazwisko.text.clear()
            binding.editNrAlbumu.text.clear()

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
         * @return A new instance of fragment DodajUczniaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DodajUczniaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}