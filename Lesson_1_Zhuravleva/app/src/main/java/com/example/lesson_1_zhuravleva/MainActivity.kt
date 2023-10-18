package com.example.lesson_1_zhuravleva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1_zhuravleva.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intentFirstActivity = FirstActivity.createIntent(this)
        val intentSecondActivity = SecondActivity.createIntent(this)
        binding.apply {
            btnToFirstActivity.setOnClickListener {
                startActivity(intentFirstActivity)
            }
            btnToSecondActivity.setOnClickListener {
                startActivity(intentSecondActivity)
            }
        }
    }
}
