package com.example.lesson_3_zhuravleva.presentation.ui.catalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.CatalogItemBinding
import com.example.lesson_3_zhuravleva.domain.catalog.Product
import javax.inject.Inject

class CatalogAdapter @Inject constructor(private val listener: Listener):
    RecyclerView.Adapter<CatalogViewHolder>() {

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product):
                    Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product):
                    Boolean {
                return oldItem == newItem
            }
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_UTIL)

    fun submitList(products: List<Product>){
        differ.submitList(products)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val binding = CatalogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }

    interface Listener{
        fun onClick(product: Product)
        fun onClickButton(product: Product)
    }

}
