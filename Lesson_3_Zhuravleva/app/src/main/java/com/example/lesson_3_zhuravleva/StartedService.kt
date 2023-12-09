package com.example.lesson_3_zhuravleva

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class StartedService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("StartedService", "onCreate called")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StartedService", "onStartCommand called")
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("StartedService", "onDestroy called")
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}