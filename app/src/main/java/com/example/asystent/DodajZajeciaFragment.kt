package com.example.asystent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.asystent.databinding.FragmentDodajZajeciaBinding
import kotlinx.coroutines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DodajZajeciaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DodajZajeciaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentDodajZajeciaBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var zajeciaList : MutableList<Zajecia>

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
        _binding = FragmentDodajZajeciaBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        binding.buttonDodajZajecia.setOnClickListener {
            writeData()
        }
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            zajeciaList = appDatabase.zajeciaDao().getAll()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun writeData() {
        val nazwaZajec = binding.editNazwaZajec.text.toString()
        var dzienTygodnia = binding.editDzienTygodnia.text.toString()
        val godzina = binding.editGodzina.text.toString()
        val godzinaRegex = Regex("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]\$")

        if (dzienTygodnia == "Poniedzialek") dzienTygodnia = "Poniedziałek"
        if (dzienTygodnia == "Sroda") dzienTygodnia = "Środa"
        if (dzienTygodnia == "Piatek") dzienTygodnia = "Piątek"

        val dniTygodnia = listOf("Poniedziałek", "Wtorek", "Środa", "Czwartek", "Piątek")

        if (!dniTygodnia.contains(dzienTygodnia)) {
            Toast.makeText(activity, "Enter a valid day of the week", Toast.LENGTH_SHORT).show()
        } else if (!godzina.matches(godzinaRegex)) {
            Toast.makeText(activity, "Enter a valid format for day time", Toast.LENGTH_SHORT).show()
        } else if (nazwaZajec.isEmpty() || dzienTygodnia.isEmpty() || godzina.isEmpty()) {
            Toast.makeText(activity, "Please enter all data", Toast.LENGTH_SHORT).show()
        } else if (zajeciaList.find{ it == Zajecia(nazwaZajec, dzienTygodnia, godzina)} != null) {
            Toast.makeText(activity, "This element already exists", Toast.LENGTH_SHORT).show()
        } else {
            val zajecia = Zajecia(
                nazwaZajec, dzienTygodnia, godzina
            )
            GlobalScope.launch(Dispatchers.IO) {
                appDatabase.zajeciaDao().insert(zajecia)
                zajeciaList.add(zajecia)
            }

            binding.editNazwaZajec.text.clear()
            binding.editDzienTygodnia.text.clear()
            binding.editGodzina.text.clear()

            Toast.makeText(activity, "Successfully added", Toast.LENGTH_SHORT).show()

        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DodajZajeciaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DodajZajeciaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}