package com.example.lesson_3_zhuravleva.domain.order

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SelectedProduct(
    val id: String,
    val title: String,
    val department: String,
    val preview: String,
    val size: String,
    val price: Int,
): Parcelable