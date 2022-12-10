package com.example.asystent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ZajeciaAdapter (private val zajeciaList : MutableList<Zajecia>) : RecyclerView.Adapter<ZajeciaAdapter.ZajeciaViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ZajeciaViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item_zajecia, parent, false)
        return ZajeciaViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ZajeciaViewHolder, position: Int) {
        val currentItem = zajeciaList[position]
        holder.nazwaZajec.text = currentItem.nazwaZajec
        holder.dzien.text = currentItem.dzien
        holder.godzina.text = currentItem.godzina
    }

    override fun getItemCount(): Int {
        return zajeciaList.size
    }
    class ZajeciaViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nazwaZajec : TextView = itemView.findViewById(R.id.list_item_zajecia)
        val dzien : TextView = itemView.findViewById(R.id.list_item_dzien)
        val godzina : TextView = itemView.findViewById(R.id.list_item_godzina)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}
