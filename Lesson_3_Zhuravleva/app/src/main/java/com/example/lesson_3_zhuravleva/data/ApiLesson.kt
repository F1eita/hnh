package com.example.lesson_3_zhuravleva.data

import com.example.lesson_3_zhuravleva.data.requestmodel.RequestLogin
import com.example.lesson_3_zhuravleva.data.requestmodel.RequestOrder
import com.example.lesson_3_zhuravleva.data.responsemodel.BaseResponse
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseLogin
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseOrder
import com.example.lesson_3_zhuravleva.data.responsemodel.ResponseProduct
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiLesson {

    @PUT("user/signin")
    suspend fun login(
        @Body requestLogin: RequestLogin,
    ): BaseResponse<ResponseLogin>

    @GET("products")
    suspend fun getProductsList(
        @Query("PageNumber") pageNumber: Int = 1,
        @Query("PageSize") pageSize: Int = 20
    ): BaseResponse<List<ResponseProduct>>

    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: String
    ): BaseResponse<ResponseProduct>

    @POST("orders/checkout")
    suspend fun postOrder(
        @Body requestOrder: RequestOrder,
    ): BaseResponse<ResponseOrder>
}