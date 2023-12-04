package com.example.lesson_3_zhuravleva.presentation.ui.product.size

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_zhuravleva.databinding.SizeItemBinding
import com.example.lesson_3_zhuravleva.domain.product.Size
import javax.inject.Inject

class SizeAdapter @Inject constructor(private val listener: Listener):
    RecyclerView.Adapter<SizeViewHolder>() {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Size>() {
            override fun areItemsTheSame(oldItem: Size, newItem: Size):
                    Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Size, newItem: Size):
                    Boolean {
                return oldItem == newItem
            }
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_UTIL)

    fun submitList(sizes: List<Size>){
        differ.submitList(sizes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SizeViewHolder {
        val binding = SizeItemBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false)
        return SizeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: SizeViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }


    interface Listener{
        fun onClick(size: Size)
    }

}