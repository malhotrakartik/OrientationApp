package com.example.orientationapp

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.room.Room
import com.example.orientationapp.database.ListDatabase
import com.example.orientationapp.database.ListEntity
import org.w3c.dom.Text

class ListHolder : AppCompatActivity() {



    lateinit var previousList : ListView
    lateinit var txtMax : TextView
    lateinit var txtMin : TextView

    lateinit var progressLayout: RelativeLayout
    lateinit var progressBar : ProgressBar
    val lists = arrayListOf<String>()

    var dbList = listOf<ListEntity>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_holder)
        txtMax = findViewById(R.id.txtMax)
        txtMin = findViewById(R.id.txtMin)
        previousList = findViewById(R.id.previousList)
        progressLayout = findViewById(R.id.progressLayout)
        progressBar = findViewById(R.id.progressBar)
        var maxi : Float = Float.MIN_VALUE
        var mini : Float = Float.MAX_VALUE
        dbList = RetrieveFavourites(
            this@ListHolder
        ).execute().get()

        for (list in dbList) {
            var pitch =list.listPitch
            var roll = list.listRoll
            lists.add("Roll : $roll             Pitch : $pitch")
            maxi = Math.max(pitch.toFloat(),maxi)
            mini = Math.min(pitch.toFloat(),mini)
        }
        txtMax.text = "Maximum pitch: $maxi"
        txtMin.text = "Minimum pitch: $mini "
        progressLayout.visibility = View.GONE
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, lists)
        previousList.adapter = adapter








    }

    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<ListEntity>>() {
        val db = Room.databaseBuilder(context , ListDatabase::class.java, "lists-db").build()
        override fun doInBackground(vararg params: Void?): List<ListEntity> {
            return db.listDao().getAllLists()

        }
    }
}
