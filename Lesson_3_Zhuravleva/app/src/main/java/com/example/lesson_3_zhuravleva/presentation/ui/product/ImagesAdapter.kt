package com.example.lesson_3_zhuravleva.presentation.ui.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_zhuravleva.databinding.ProductImagesItemBinding
import javax.inject.Inject

class ImagesAdapter @Inject constructor(private val listener: Listener):
    RecyclerView.Adapter<ImageViewHolder>() {

    var selectedItem: ImageItem? = null
        set(value){
            field = value
            setIsSelected()
        }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<ImageItem>() {
            override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem):
                    Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem):
                    Boolean {
                return oldItem == newItem
            }
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_UTIL)

    private fun submitList(images: List<ImageItem>){
        differ.submitList(images)
    }

    fun setList(images: List<String>, preview: String){
        val imagesList = mutableListOf<ImageItem>()
        for (i in 0..2){
            var image: String? = null
            when(i){
                0 -> image = preview
                in 1..images.size -> image = images[i-1]
            }
            imagesList.add(ImageItem(i, image, false))
        }
        submitList(imagesList)
        selectedItem = imagesList[0]
    }
    private fun setIsSelected(){
        val newList = mutableListOf<ImageItem>()
        differ.currentList.forEach{
            newList.add(it.copy(isSelected = it == selectedItem))
        }
        submitList(newList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ProductImagesItemBinding.inflate(LayoutInflater.from(parent.context), parent,
            false)
        return ImageViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(differ.currentList[position], listener)
    }


    interface Listener{
        fun onClick(image: ImageItem)
    }

}