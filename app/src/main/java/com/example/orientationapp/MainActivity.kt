package com.example.orientationapp

import android.animation.ValueAnimator
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    lateinit var mainImage: ImageView
    var animationDuration :  Long = 1000
    private lateinit var sensorManager: SensorManager
    lateinit var txtRoll : TextView
    var x : String = ""
    var y : String = ""
    var prev : String = ""



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtRoll = findViewById(R.id.txtRoll)
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


                    txtRoll.text = x
                }
            }






        }
        sensorManager.registerListener(se,list.get(0),SensorManager.SENSOR_DELAY_NORMAL)




            




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




}
