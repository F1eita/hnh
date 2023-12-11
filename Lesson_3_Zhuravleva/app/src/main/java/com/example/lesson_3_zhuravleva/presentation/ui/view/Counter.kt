package com.example.lesson_3_zhuravleva.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.CounterBinding

class Counter @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var binding: CounterBinding? = null

    lateinit var callback: () -> Unit

    private var count = 1
        set(value){
            field = value
            binding!!.tvCount.text = value.toString()
            when(value){
                1 -> binding!!.btnMinus.isEnabled = false
                10 -> binding!!.btnPlus.isEnabled = false
                else -> binding!!.apply {
                    btnMinus.isEnabled = true
                    btnPlus.isEnabled = true
                }
            }
            callback()
        }

    init {
        binding = CounterBinding.bind(
            LayoutInflater.from(context).inflate(
                R.layout.counter, this,
                true
            )
        )
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        binding?.apply {
            tvCount.text = count.toString()
            btnPlus.setOnClickListener {
                count++
            }
            btnMinus.setOnClickListener {
                count--
            }
        }
    }

    fun getCount(): Int{
        return count
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

}