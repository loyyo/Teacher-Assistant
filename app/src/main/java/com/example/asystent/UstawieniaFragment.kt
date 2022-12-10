package com.example.asystent

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.asystent.databinding.FragmentUstawieniaBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UstawieniaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UstawieniaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentUstawieniaBinding? = null
    private val binding get() = _binding!!
    private lateinit var appDatabase: AppDatabase

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
        _binding = FragmentUstawieniaBinding.inflate(inflater, container, false)

        appDatabase = AppDatabase.getDatabase(requireContext())

        binding.buttonUsunWszystko.setOnClickListener {
            deleteAllData()
        }

        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun deleteAllData() {
        (activity?.let {
            val builder =
                AlertDialog.Builder(it).setMessage("DO YOU REALLY WANT TO DELETE ALL DATA?!?!?")
            builder.apply {
                setPositiveButton("DO IT",
                    DialogInterface.OnClickListener { dialog, id ->
                        GlobalScope.launch {
                            appDatabase.zajeciaDao().deleteAll()
                            appDatabase.uczniowieDao().deleteAll()
                        }
                        Toast.makeText(
                            activity,
                            "Successfully deleted all data",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                setNegativeButton("GOD, NO!!!",
                    DialogInterface.OnClickListener { dialog, id ->
    //                            dialog.cancel()
                    })
            }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null"))?.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UstawieniaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UstawieniaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}