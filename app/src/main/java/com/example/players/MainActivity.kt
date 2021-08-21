package com.example.players

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.players.dataitem.CountryItem
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity(), CountryListener {


    private val url = URL("https://test.oye.direct/players.json")
    private val list: ArrayList<CountryItem> = ArrayList()
    private var adapter = CountryRVAdapter(list.toList(), this)
    private var countryRecyclerView : RecyclerView? = null
    private val handler = Handler()
    var data = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        countryRecyclerView = findViewById<RecyclerView>(R.id.countryNameRecyclerView)
        countryRecyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = CountryRVAdapter(list.toList(), this)
        countryRecyclerView?.adapter = adapter

        getData.start()
    }


    private val getData = Thread {

        try {
            val httpByteArray = url.readBytes()

            val inputStream = ByteArrayInputStream(httpByteArray)
            val buffer = InputStreamReader(inputStream)

            buffer.forEachLine {
                data += it
            }

            if (data.isNotEmpty()){
                val jsonObject = JSONObject(data)
                val countries = jsonObject.keys()

                list.clear()
                countries.forEach {
                    list.add(CountryItem(it))
                    Log.d("GetData","Country:: $it")
                }

            }

        }catch (e: Exception){
            e.stackTrace
        }

        handler.post{
            adapter = CountryRVAdapter(list.sortedBy { it.name }.toList(), this)
            countryRecyclerView?.adapter = adapter
            adapter.notifyDataSetChanged()
            Log.d("GetData","Country:: $list")
        }
    }

    override fun onItemClicked(item: CountryItem) {
        Toast.makeText(this, "Showing ${item.name}'s players", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, PlayersActivity::class.java)
        intent.putExtra("COUNTRY_NAME", item.name)
        intent.putExtra("DATA", data)
        startActivity(intent)
    }
}