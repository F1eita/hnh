package com.example.lesson_2_zhuravleva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_2_zhuravleva.databinding.ActivityMainBinding
import com.example.lesson_2_zhuravleva.firstpart.FirstActivity
import com.example.lesson_2_zhuravleva.secondpart.SecondActivity
import com.example.lesson_2_zhuravleva.thirdpart.ThirdActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val firstIntent = FirstActivity.createIntent(this)
        val secondIntent = SecondActivity.createIntent(this)
        val thirdIntent = ThirdActivity.createIntent(this)
        binding.apply {
            btnToFirstActivity.setOnClickListener {
                startActivity(firstIntent)
            }
            btnToSecondActivity.setOnClickListener {
                startActivity(secondIntent)
            }
            btnToThirdActivity.setOnClickListener {
                startActivity(thirdIntent)
            }
        }
    }
}
