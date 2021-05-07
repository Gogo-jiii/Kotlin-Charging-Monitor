package com.example.chargingmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import java.lang.StringBuilder

class ChargingStateDetector : BroadcastReceiver() {


    override fun onReceive(context: Context, intent: Intent) {
        handleMediaplayer(context, intent)
    }

    private fun handleMediaplayer(context: Context, intent: Intent) {
        if (!isDeviceCharging(intent) || isDeviceFullyCharged(intent)) {
            if(MediaManager.getInstance().isPlaying() == true){
                MediaManager.getInstance().stop()
            }else{
                MediaManager.getInstance().play(context)
            }
        }
    }

    private fun isDeviceCharging(intent: Intent): Boolean {
        val status: Int = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val isCharging: Boolean = status == BatteryManager.BATTERY_STATUS_CHARGING
                || status == BatteryManager.BATTERY_STATUS_FULL
        return isCharging
    }

    private fun isDeviceFullyCharged(intent: Intent): Boolean {
        val batteryPct: Float? = intent.let { intent ->
            val level: Int = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale: Int = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
            level * 100 / scale.toFloat()
        }

        return batteryPct == 100f
    }
}