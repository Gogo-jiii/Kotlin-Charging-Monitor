package com.example.chargingmanager

import android.content.Context
import android.content.res.AssetFileDescriptor
import android.media.MediaPlayer
import java.io.IOException


class MediaManager {

    companion object {
        private var mp: MediaPlayer? = null
        private var instance: MediaManager? = null
        private lateinit var afd: AssetFileDescriptor
        fun getInstance(): MediaManager {
            if (instance == null) {
                instance = MediaManager()
                mp = MediaPlayer()
            }
            return instance as MediaManager
        }
    }


    public fun play(context: Context) {
        try {
            afd = context.getAssets().openFd("not_charging_tone.mp3")
            mp?.let {
                it.reset()
                it.setDataSource(afd.fileDescriptor)
                it.prepare()
                it.start()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    public fun stop() {
        mp?.let {
            if (it.isPlaying) {
                it.stop()

                afd?.let {
                    it.close()
                }
            }
        }
    }

    fun isPlaying(): Boolean? {
        return mp?.isPlaying
    }
}