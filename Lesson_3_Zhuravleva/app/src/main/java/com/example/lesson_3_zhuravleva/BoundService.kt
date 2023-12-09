package com.example.lesson_3_zhuravleva

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class BoundService : Service() {

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): BoundService {
            return this@BoundService
        }
    }

    override fun onCreate() {
        Log.d("BoundService", "onCreate called")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder {
        Log.d("BoundService", "onBind called")
        return MyBinder()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("BoundService", "onUnbind called")
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("BoundService", "onDestroy called")
        super.onDestroy()
    }
}