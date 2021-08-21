package com.example.players

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.players.dataitem.PlayerItem
import org.json.JSONObject

class PlayersActivity : AppCompatActivity() {

    var data = ""
    var countryName = ""
    private val list: ArrayList<PlayerItem> = ArrayList()
    private var adapter = PlayerRVAdapter(this, list.toList())
    private var playerRV: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        data = intent.getStringExtra("DATA")!!
        countryName = intent.getStringExtra("COUNTRY_NAME")!!

        getPlayers(countryName)

        playerRV = findViewById(R.id.playerRecyclerView)
        playerRV?.layoutManager = LinearLayoutManager(this)

        adapter = PlayerRVAdapter(this, list.toList())
        playerRV?.adapter = adapter

        val title : TextView = findViewById(R.id.titleTextView)
        title.text = "All Players of $countryName"
    }


    private fun getPlayers(countryName: String){
        try {
            if (data.isNotEmpty()){
                val jsonObject = JSONObject(data)
                val cp = jsonObject.getJSONArray(countryName)

                list.clear()

                for(i in 0..cp.length()){
                    val player = cp.getJSONObject(i)
                    val fullName = player.getString("name").split(" ")
                    val isCaptain = player.getBoolean("captain")

                    list.add(PlayerItem(fullName[0], fullName[1], isCaptain))
                    adapter = PlayerRVAdapter(this, list.toList())
                    playerRV?.adapter = adapter
                    adapter.notifyDataSetChanged()
                    Log.d("GetPlayers", "Players :: $list")
                }
            }
        }catch (e: Exception){
            e.stackTrace
        }
    }
}