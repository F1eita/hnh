package com.example.lesson_3_zhuravleva.presentation.ui.catalog

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.CatalogItemBinding
import com.example.lesson_3_zhuravleva.domain.catalog.Product

class CatalogViewHolder(private val binding: CatalogItemBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(product: Product, listener: CatalogAdapter.Listener){
        binding.apply {
            tvTitle.text = product.title
            tvDepartment.text = product.department
            tvPrice.text = itemView.context.getString(R.string.ruble, product.price.toString())
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
                        .getDimension(R.dimen.corners_radius_50).toInt())
                )
            ).into(binding.imageView)
        itemView.setOnClickListener {
            listener.onClick(product)
        }
    }
}