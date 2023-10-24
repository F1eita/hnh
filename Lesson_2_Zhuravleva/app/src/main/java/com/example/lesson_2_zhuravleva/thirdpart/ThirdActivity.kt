package com.example.lesson_2_zhuravleva.thirdpart

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.ActivityThirdBinding

class ThirdActivity : AppCompatActivity() {

    companion object{
        fun createIntent(context: Context) = Intent(context, ThirdActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("activity lifecycle", "OnCreate()")
        val binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerOne, OneFragment())
            commit()
        }
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerTwo, TwoFragment())
            commit()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("activity lifecycle", "OnStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("activity lifecycle", "OnResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("activity lifecycle", "OnPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("activity lifecycle", "OnStop()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("activity lifecycle", "OnDestroy()")
    }
}
