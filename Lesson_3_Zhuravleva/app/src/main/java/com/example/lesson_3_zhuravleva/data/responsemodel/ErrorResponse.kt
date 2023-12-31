package com.example.lesson_3_zhuravleva.data.responsemodel

import com.google.gson.annotations.SerializedName

data class ErrorBaseResponse(
    @SerializedName("error") val error: ErrorErrorResponse,
)

class ErrorErrorResponse(
    @SerializedName("message") val message: String,
)