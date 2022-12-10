package com.example.asystent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystent.databinding.FragmentOcenyBinding
import com.example.asystent.databinding.FragmentUczniowieBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OcenyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OcenyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int? = null

    private var _binding: FragmentOcenyBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var adapter: OcenyAdapter
    private lateinit var recyclerView: RecyclerView

    private var ocenyList : MutableList<Oceny> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getInt(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOcenyBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            appDatabase.ocenyDao().getAll()
                .filter { it.nazwaZajec == param1 }
                .filter { it.nrAlbumu == param2}
                .forEach {
                    ocenyList.add(it)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OcenyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            OcenyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.oceny_recycler_view)
        recyclerView.layoutManager = layoutManager
        adapter = OcenyAdapter(ocenyList)
        recyclerView.adapter = adapter
    }
}