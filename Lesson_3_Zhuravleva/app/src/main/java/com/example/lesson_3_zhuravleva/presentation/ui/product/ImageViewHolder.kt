package com.example.lesson_3_zhuravleva.presentation.ui.product

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.lesson_3_zhuravleva.R
import com.example.lesson_3_zhuravleva.databinding.ProductImagesItemBinding

data class ImageItem(
    val id: Int,
    val image: String?,
    val isSelected: Boolean
)

class ImageViewHolder(private val binding: ProductImagesItemBinding)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(imageItem: ImageItem, listener: ImagesAdapter.Listener){
        Glide.with(binding.imageView)
            .load(imageItem.image ?: R.drawable.base_image)
            .transform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCorners(this.itemView.resources
                        .getDimension(R.dimen.corners_radius_25).toInt())
                )
            ).into(binding.imageView)
        itemView.isSelected = imageItem.isSelected
        itemView.setOnClickListener {
            listener.onClick(imageItem)
        }
    }
}