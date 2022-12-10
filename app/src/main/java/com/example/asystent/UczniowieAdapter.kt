package com.example.asystent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UczniowieAdapter (private val uczniowieList : MutableList<Uczniowie>) : RecyclerView.Adapter<UczniowieAdapter.UczniowieViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UczniowieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_uczniowie, parent, false)
        return UczniowieViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: UczniowieViewHolder, position: Int) {
        val currentItem = uczniowieList[position]
        holder.imieNazwisko.text = currentItem.imieNazwisko
        holder.nrAlbumu.text = currentItem.nrAlbumu.toString()
    }

    override fun getItemCount(): Int {
        return uczniowieList.size
    }
    class UczniowieViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val imieNazwisko : TextView = itemView.findViewById(R.id.list_item_imie_nazwisko)
        val nrAlbumu : TextView = itemView.findViewById(R.id.list_item_nr_albumu)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
