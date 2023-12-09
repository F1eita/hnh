package com.example.lesson_3_zhuravleva

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class StartedAndBoundService: Service() {

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): StartedAndBoundService {
            return this@StartedAndBoundService
        }
    }

    override fun onCreate() {
        Log.d("StartedAndBoundService", "onCreate called")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("StartedAndBoundService", "onStartCommand called")
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("StartedAndBoundService", "onBind called")
        return MyBinder()
    }

    override fun onRebind(intent: Intent?) {
        Log.d("StartedAndBoundService", "onRebind called")
        super.onRebind(intent)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("StartedAndBoundService", "onUnbind called")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("StartedAndBoundService", "onDestroy called")
        super.onDestroy()
    }

}