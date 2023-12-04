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

class CatalogAdapter(private val listener: Listener):
    RecyclerView.Adapter<CatalogAdapter.CatalogHolder>() {

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

    class CatalogHolder(private val binding: CatalogItemBinding)
        : RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product, listener: Listener){
            binding.apply {
                tvTitle.text = product.title
                tvDepartment.text = product.department
                tvPrice.text = "${product.price}₽"
                btnBuy.setOnClickListener {
                    listener.onClickButton(product)
                }
            }
            Glide.with(binding.imageView)
                .load(product.preview)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        RoundedCorners(this.itemView.resources
                            .getDimension(R.dimen.corners_radius).toInt())
                    )
                ).into(binding.imageView)
            itemView.setOnClickListener {
                listener.onClick(product)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogHolder {
        val binding = CatalogItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CatalogHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: CatalogHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }

    interface Listener{
        fun onClick(product: Product)
        fun onClickButton(product: Product)
    }

}
