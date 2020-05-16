package com.example.ballrun

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), SensorEventListener {
    private var sensorManager: SensorManager? = null
    private var canvasView: CanvasView? = null
    private var sensorX = 0f
    private var sensorY = 0f
    private var sensorZ = 0f
    private val period = 100
    private val handler: Handler = Handler()
    private var runnable: Runnable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get an instance of the SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        canvasView = CanvasView(this, null)
        setContentView(canvasView)
        timerSet()
    }

    override fun onResume() {
        super.onResume()
        val accel: Sensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        // Listenerの登録
        sensorManager!!.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        // Listenerを解除
        sensorManager!!.unregisterListener(this)
        stopTimerTask()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            sensorX = event.values[0]
            sensorY = event.values[1]
            sensorZ = event.values[2]
        }
    }

    private fun timerSet() {
        runnable = object : Runnable {
            override fun run() {
                canvasView!!.setPosition(sensorX, sensorY)
                handler.postDelayed(this, period.toLong())
            }
        }
        handler.post(runnable!!)
    }

    private fun stopTimerTask() {
        handler.removeCallbacks(runnable!!)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
