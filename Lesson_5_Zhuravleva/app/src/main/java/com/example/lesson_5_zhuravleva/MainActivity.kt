package com.example.lesson_5_zhuravleva

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

const val DAY_MILLIS: Long = 86400000

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val simpleDateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val dataList = mutableMapOf<String, Int>()
        val currDateInMillis = Date().time
        for (i in 8 downTo 0){
            val date = simpleDateFormat.format(Date(currDateInMillis - DAY_MILLIS*i))
            val value = (1..100).random()
            dataList[date] = value
        }
        findViewById<ColumnChart>(R.id.columnChart).apply{
            this.dataList = dataList
        }
    }
}