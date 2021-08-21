package com.example.players

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.players.dataitem.PlayerItem

class PlayerRVAdapter (private val context: Context, private val playerList: List<PlayerItem>) : RecyclerView.Adapter<PlayerRVAdapter.PlayerViewHolder>()
{

    inner class PlayerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.playerName)
        val tag : TextView = itemView.findViewById(R.id.tag)
        val playerBg : CardView = itemView.findViewById(R.id.playerBackground)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {

        return PlayerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlayerRVAdapter.PlayerViewHolder, position: Int) {
        val currentItem = playerList[position]
        holder.name.text = "${currentItem.firstName} ${currentItem.lastName}"

        if (currentItem.isCaptain){
            holder.tag.text = context.getString(R.string.captain)
            holder.playerBg.backgroundTintList = ContextCompat.getColorStateList(context,
                android.R.color.holo_blue_light
            )
        }
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

}