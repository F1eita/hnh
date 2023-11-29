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
    private var count = 1

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
                if (!btnMinus.isEnabled) btnMinus.isEnabled = true
                tvCount.text = count.toString()
            }
            btnMinus.setOnClickListener {
                count--
                tvCount.text = count.toString()
                if (count == 1){
                    btnMinus.isEnabled = false
                }
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