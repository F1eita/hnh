package com.example.lesson_2_zhuravleva.secondpart

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_2_zhuravleva.R
import com.example.lesson_2_zhuravleva.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    companion object{
        fun createIntent(context: Context) = Intent(context, SecondActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentFirst = FirstFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, fragmentFirst)
            commit()
        }
    }
}
