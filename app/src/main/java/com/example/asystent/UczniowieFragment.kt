package com.example.asystent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystent.databinding.FragmentUczniowieBinding
import com.example.asystent.databinding.FragmentZajeciaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"

/**
 * A simple [Fragment] subclass.
 * Use the [UczniowieFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UczniowieFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null

    private var _binding: FragmentUczniowieBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var adapter: UczniowieAdapter
    private lateinit var recyclerView: RecyclerView

    private var uczniowieList : MutableList<Uczniowie> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUczniowieBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            val uczniowieFromDB = appDatabase.uczniowieDao().getAll()
            if (param1 == null || param2 == null || param3 == null) {
                uczniowieFromDB.forEach {
                    uczniowieList.add(it)
                }
            } else {
                appDatabase.zajeciaUczniaDao().getAll().filter{ it.nazwaZajec == param1 }.forEachIndexed { i, value ->
                    uczniowieFromDB.find { it.nrAlbumu == value.nrAlbumu }
                        ?.let { uczniowieList.add(it) }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment UczniowieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
            UczniowieFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM3, param3)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.uczniowie_recycler_view)
        recyclerView.layoutManager = layoutManager
        adapter = UczniowieAdapter(uczniowieList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : UczniowieAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                if (param1 == null || param2 == null || param3 == null) {
                    val fragment : Fragment = ZajeciaFragment.newInstance(
                        uczniowieList[position].imieNazwisko,
                        uczniowieList[position].nrAlbumu
                    )
                    fragmentTransaction.replace(R.id.frameLayout, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    activity!!.title = uczniowieList[position].imieNazwisko
                } else {
                    val fragment : Fragment = OcenyFragment.newInstance(
                        param1!!,
                        uczniowieList[position].nrAlbumu
                    )
                    fragmentTransaction.replace(R.id.frameLayout, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    activity!!.title = "$param1 - ${uczniowieList[position].imieNazwisko}"
                }
            }
        })
    }
}