package com.example.asystent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystent.databinding.FragmentZajeciaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ZajeciaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ZajeciaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: Int? = null

    private var _binding: FragmentZajeciaBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

    private lateinit var adapter: ZajeciaAdapter
    private lateinit var recyclerView: RecyclerView

    private var zajeciaList : MutableList<Zajecia> = mutableListOf()

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
        // Inflate the layout for this fragment
        _binding = FragmentZajeciaBinding.inflate(inflater, container, false)
        appDatabase = AppDatabase.getDatabase(requireContext())
        dataInitialize()
        return binding.root
    }

    fun dataInitialize() {
        GlobalScope.launch(Dispatchers.IO) {
            val zajeciaFromDB = appDatabase.zajeciaDao().getAll()
            if (param1 == null || param2 == null) {
                zajeciaFromDB.forEach {
                    zajeciaList.add(it)
                }
            } else {
                appDatabase.zajeciaUczniaDao().getAll().filter{ it.nrAlbumu == param2 }.forEachIndexed { i, value ->
                    zajeciaFromDB.find { it.nazwaZajec == value.nazwaZajec }
                        ?.let { zajeciaList.add(it) }
                }
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UczniowieFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: Int) =
            ZajeciaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, param2)
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
        recyclerView = view.findViewById(R.id.zajecia_recycler_view)
        recyclerView.layoutManager = layoutManager
        adapter = ZajeciaAdapter(zajeciaList)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : ZajeciaAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                if (param1 == null || param2 == null) {
                    val fragment : Fragment = UczniowieFragment.newInstance(
                        zajeciaList[position].nazwaZajec,
                        zajeciaList[position].dzien,
                        zajeciaList[position].godzina
                    )
                    fragmentTransaction.replace(R.id.frameLayout, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    activity!!.title = zajeciaList[position].nazwaZajec
                }
                else {
                    val fragment : Fragment = OcenyFragment.newInstance(
                        zajeciaList[position].nazwaZajec,
                        param2!!
                    )
                    fragmentTransaction.replace(R.id.frameLayout, fragment)
                    fragmentTransaction.addToBackStack(null)
                    fragmentTransaction.commit()
                    activity!!.title = "${zajeciaList[position].nazwaZajec} - $param1"
                }
            }
        })
    }
}