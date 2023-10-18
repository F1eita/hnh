package com.example.lesson_1_zhuravleva

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import com.example.lesson_1_zhuravleva.databinding.ActivitySecondBinding
import java.util.regex.Pattern

class SecondActivity : AppCompatActivity() {

    companion object {
        fun createIntent(context: Context) = Intent(context, SecondActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val studentsInfo = HashMap<Long, Student>()
        binding.apply{
            edtStudentInfo.setOnKeyListener { _, keyCode, keyEvent ->
                if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    val student = getStudent(edtStudentInfo.text.toString())
                    if (student == null){
                        Toast.makeText(this@SecondActivity, getString(R.string.error_msg),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        studentsInfo[student.id] = student
                    }
                    return@setOnKeyListener true
                }
                false
            }
            btnShowInfoList.setOnClickListener {
                tvStudentsInfoList.text = ""
                for (student in studentsInfo.values){
                    tvStudentsInfoList.append(student.toString() + "\n")
                }
            }
        }
    }

    private fun getStudent(string: String): Student?{
        var student: Student? = null
        val infoList = string.split(" ")
        val nameSample = Pattern.compile("[А-Я]+")
        val gradeSample = Pattern.compile("\\d[А-Я]")
        val yearSample = Pattern.compile("\\d{4}")
        if (checkField(infoList[0].uppercase(), nameSample)
            && checkField(infoList[1].uppercase(), nameSample)
            && checkField(infoList[2], gradeSample) && checkField(infoList[3], yearSample)
            && infoList.size == 4){
            student = Student(System.currentTimeMillis(),
                infoList[0], infoList[1], infoList[2], infoList[3])
        }
        return student
    }

    private fun checkField(string: String, pattern: Pattern): Boolean{
        val matcher = pattern.matcher(string)
        return matcher.matches()
    }
}
