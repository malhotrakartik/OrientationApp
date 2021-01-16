package com.example.orientationapp

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.orientationapp.database.ListDatabase
import com.example.orientationapp.database.ListEntity
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var btnPrevious : Button
    lateinit var mainImage: ImageView
    var animationDuration :  Long = 1000
    private lateinit var sensorManager: SensorManager

    var x : String = ""
    var y : String = ""
    var dbList = listOf<ListEntity>()





    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPrevious = findViewById(R.id.btnPrevious)

        mainImage = findViewById(R.id.mainImage)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        var list = sensorManager.getSensorList(Sensor.TYPE_ORIENTATION)

//        if (sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null) {
//            Toast.makeText(this@MainActivity,"sensor is available",Toast.LENGTH_LONG).show()
//        } else {
//            Toast.makeText(this@MainActivity,"sensor is not available",Toast.LENGTH_LONG).show()
//
//        }

        var se = object : SensorEventListener{

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

            }
            override fun onSensorChanged(event: SensorEvent) {
                Log.d("debug", "Sensor Changed")
                if (event.sensor.type == Sensor.TYPE_ORIENTATION) {
                    Log.d("debug", event.values[0].toString())
                    val mAzimuth = event.values[0]
                    val mPitch = event.values[1]
                    val mRoll = event.values[2]
                    Log.d("debug", "mAzimuth :" + java.lang.Float.toString(mAzimuth))
                    Log.d("debug", "mPitch :" + java.lang.Float.toString(mPitch))
                    Log.d("debug", "mRoll :$mRoll")
                    y = mPitch.toString()
                    x = mRoll.toString()


                    animateLogo(x,y)
                    val uniqueID = UUID.randomUUID().toString()

                    val listEntity = ListEntity(
                       uniqueID,
                        mRoll.toString(),
                        mPitch.toString()

                    )
                    dbList = ListHolder.RetrieveFavourites(
                        this@MainActivity
                    ).execute().get()
                    if(dbList.size < 500){
                        DBAsyncTask(this@MainActivity,listEntity,uniqueID,2).execute()
//                        count += 1;
                    }










                }
            }






        }
        sensorManager.registerListener(se,list.get(0),SensorManager.SENSOR_DELAY_NORMAL)
        btnPrevious.setOnClickListener {
            val intent = Intent(this@MainActivity , ListHolder::class.java)
            startActivity(intent)
        }




            




    }


    private fun animateLogo(x: String,y:String) {

        val translationXFrom = x.toFloat()*3
        val translationXTo = x.toFloat()*3

        val translationYFrom = y.toFloat()*3
        val translationYTo = y.toFloat()*3
        val valueAnimatorX = ValueAnimator.ofFloat(translationXFrom, translationXTo).apply {
            interpolator = LinearInterpolator()
            duration = 1000
        }
        val valueAnimatorY = ValueAnimator.ofFloat(translationYFrom,translationYTo).apply {
            interpolator = LinearInterpolator()
            duration = 1000


        }
        valueAnimatorX.addUpdateListener {
            val value = it.animatedValue as Float
            mainImage?.translationX = value

        }
        valueAnimatorY.addUpdateListener {
            val value = it.animatedValue as Float
            mainImage?.translationY = value

        }
        valueAnimatorX.start()
        valueAnimatorY.start()
    }

















    class DBAsyncTask(val context: Context, val listEntity: ListEntity,val listId : String, val mode : Int) : AsyncTask<Void, Void, Boolean>(){

        /*mode 1 = check db if book is fav or not
         mode 2 = add to fav
         mode 3 = remove from fav*/

        val db = Room.databaseBuilder(context , ListDatabase::class.java, "lists-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode){
                1 ->{

                    val list : ListEntity? =db.listDao().getListById(listEntity.listId)
                    db.close()
                    return list!= null        //will return false if no book is present

                }

                2->{
                    db.listDao().insertList(listEntity)
                    db.close()
                    return true

                }
                3->{
                    db.listDao().deleteListById(listId)
                    db.close()
                    return true

                }
                4 -> {
                    db.listDao().getAllLists()
                    db.close()
                    return true
                }



            }
            return false
        }

    }
    class RetrieveFavourites(val context: Context) : AsyncTask<Void, Void, List<ListEntity>>() {
        val db = Room.databaseBuilder(context , ListDatabase::class.java, "lists-db").build()
        override fun doInBackground(vararg params: Void?): List<ListEntity> {
            return db.listDao().getAllLists()

        }
    }






}
