package com.example.lesson_3_zhuravleva.presentation.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.ViewLoadableButtonBinding


class LoadableButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var binding: ViewLoadableButtonBinding? = null
    var buttonText: String = ""
        set(value){
            field = value
            binding?.buttonLoadable?.text = value
        }
    var isLoading: Boolean = false
        set(value) {
            field = value
            when (value){
                true -> setStateLoading()
                false -> setStateData()
            }
        }

    init {
        binding = ViewLoadableButtonBinding.bind(
            LayoutInflater.from(context).inflate(
                R.layout.view_loadable_button, this,
                true)
        )
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadableButton,
            0, 0)
        buttonText = typedArray.getString(R.styleable.LoadableButton_buttonText).toString()
        isLoading = typedArray.getBoolean(R.styleable.LoadableButton_isLoading, false)
        typedArray.recycle()
    }

    private fun setStateLoading() = binding?.run {
        buttonLoadable.text = ""
        progressBar.isVisible = true
    }

    private fun setStateData() = binding?.run {
        buttonLoadable.text = buttonText
        progressBar.isVisible = false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding = null
    }

}