package com.example.lesson_3_zhuravleva.presentation.ui.product.size

import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_zhuravleva.databinding.SizeItemBinding
import com.example.lesson_3_zhuravleva.domain.product.Size

class SizeViewHolder(private val binding: SizeItemBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(size: Size, listener: SizeAdapter.Listener){
        binding.apply {
            if (!size.isAvailable) root.alpha = 0.5F
            root.text = size.value
            itemView.setOnClickListener {
                if (size.isAvailable) listener.onClick(size)
            }
        }
    }
}