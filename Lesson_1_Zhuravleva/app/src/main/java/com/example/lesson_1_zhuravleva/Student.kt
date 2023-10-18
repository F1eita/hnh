package com.example.lesson_1_zhuravleva


data class Student(val id: Long,
                   val name: String,
                   val surname: String,
                   val grade: String,
                   val birthdayYear: String){

    override fun toString(): String {
        return "$id  $surname $name  $grade  $birthdayYear"
    }
}
