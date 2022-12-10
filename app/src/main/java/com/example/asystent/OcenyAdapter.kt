package com.example.asystent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OcenyAdapter (private val ocenyList : MutableList<Oceny>) : RecyclerView.Adapter<OcenyAdapter.OcenyViewHolder>() {

//    private lateinit var mListener: onItemClickListener

//    interface onItemClickListener {
//        fun onItemClick(position: Int)
//    }

//    fun setOnItemClickListener(listener: onItemClickListener) {
//        mListener = listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OcenyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_oceny, parent, false)
//        return OcenyViewHolder(itemView, mListener)
        return OcenyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OcenyViewHolder, position: Int) {
        val currentItem = ocenyList[position]
        holder.nazwaOceny.text = currentItem.nazwaOceny
        holder.wartoscOceny.text = currentItem.wartoscOceny
    }

    override fun getItemCount(): Int {
        return ocenyList.size
    }
//    class OcenyViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
//        val nazwaOceny : TextView = itemView.findViewById(R.id.list_item_nazwa_oceny)
//        val wartoscOceny : TextView = itemView.findViewById(R.id.list_item_wartosc_oceny)
//
//        init {
//            itemView.setOnClickListener{
//                listener.onItemClick(adapterPosition)
//            }
//        }
//    }
    class OcenyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nazwaOceny : TextView = itemView.findViewById(R.id.list_item_nazwa_oceny)
        val wartoscOceny : TextView = itemView.findViewById(R.id.list_item_wartosc_oceny)
    }
}
