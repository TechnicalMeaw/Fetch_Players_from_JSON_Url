package com.example.players

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.players.dataitem.CountryItem

class CountryRVAdapter(private val countryList: List<CountryItem>, private val listener: CountryListener) : RecyclerView.Adapter<CountryRVAdapter.CountryViewHolder>() {

    inner class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val viewHolder = CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_name_item, parent, false))

        viewHolder.name.setOnClickListener { listener.onItemClicked(countryList[viewHolder.adapterPosition]) }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CountryRVAdapter.CountryViewHolder, position: Int) {
        val currentItem = countryList[position]
        holder.name.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return countryList.size
    }
}

interface CountryListener{
    fun onItemClicked(item: CountryItem)
}