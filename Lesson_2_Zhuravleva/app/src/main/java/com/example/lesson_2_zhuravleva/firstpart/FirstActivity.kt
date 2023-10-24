package com.example.lesson_2_zhuravleva.firstpart

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_2_zhuravleva.R

class FirstActivity : AppCompatActivity() {

    companion object{
        fun createIntent(context: Context) = Intent(context, FirstActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)
    }
}
