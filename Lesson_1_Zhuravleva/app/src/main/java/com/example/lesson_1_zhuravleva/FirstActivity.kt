package com.example.lesson_1_zhuravleva

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lesson_1_zhuravleva.databinding.ActivityFirstBinding
import java.util.TreeSet

class FirstActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, FirstActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFirstBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val students = TreeSet<String>()
        binding.apply {
            btnAddName.setOnClickListener {
                val studentsList = edtStudentName.text.toString().split(" ")
                students.addAll(studentsList)
                edtStudentName.text = null
            }
            btnShowList.setOnClickListener {
                tvStudentsList.text = ""
                for (student in students){
                    tvStudentsList.append(student + "\n")
                }
            }
        }
    }
}
