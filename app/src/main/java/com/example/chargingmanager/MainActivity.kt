package com.example.chargingmanager

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val broadcast = ChargingStateDetector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStopUpdates.setOnClickListener {
            stopUpdates()
        }

        btnStartUpdates.setOnClickListener {
            startUpdates()
        }
    }

    private fun stopUpdates() {
        unregisterBroadcast()
        finish()
    }

    private fun startUpdates() {
        registerBroadcast()
    }

    private fun registerBroadcast() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        registerReceiver(broadcast, intentFilter)
        Toast.makeText(this, "Starting the charge state updates!", Toast.LENGTH_SHORT).show()
    }

    private fun unregisterBroadcast() {
        MediaManager.getInstance().stop()
        Toast.makeText(this, "Stopping the charge state updates!", Toast.LENGTH_SHORT).show()
        unregisterReceiver(broadcast)
    }
}