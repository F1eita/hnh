package com.example.lesson_3_zhuravleva.presentation

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import com.example.lesson_3_zhuravleva.BoundService
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.StartedAndBoundService
import com.example.lesson_3_zhuravleva.StartedService
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private lateinit var boundServiceConnection: ServiceConnection
    private var boundService: BoundService? = null

    private lateinit var startedAndBoundServiceConnection: ServiceConnection
    private var startedAndBoundService: StartedAndBoundService? = null

    companion object {
        fun createStartIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK and Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        askNotificationPermission()
        getFCMToken()

        boundServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as BoundService.MyBinder
                boundService = binder.getService()
                Log.d("MainActivity", "BoundService connected")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                boundService = null
                Log.d("MainActivity", "BoundService disconnected")
            }
        }

        startedAndBoundServiceConnection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val binder = service as StartedAndBoundService.MyBinder
                startedAndBoundService = binder.getService()
                Log.d("MainActivity", "StartedAndBoundService connected")
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                startedAndBoundService = null
                Log.d("MainActivity", "StartedAndBoundService disconnected")
            }
        }

        val startedServiceIntent = Intent(this, StartedService::class.java)
        startService(startedServiceIntent)

        val boundServiceIntent = Intent(this, BoundService::class.java)
        bindService(boundServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)

        val startedAndBoundServiceIntent = Intent(this,
            StartedAndBoundService::class.java)
        startService(startedAndBoundServiceIntent)
        bindService(startedAndBoundServiceIntent, startedAndBoundServiceConnection,
            Context.BIND_AUTO_CREATE)
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    //on fail
                    return@OnCompleteListener
                }
                val token = task.result

                Log.d("registration token", token)
            }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(boundServiceConnection)
        unbindService(startedAndBoundServiceConnection)
    }
}