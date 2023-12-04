package com.example.lesson_3_zhuravleva.data.responsemodel

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(
    @SerializedName("data") val data: T,
)