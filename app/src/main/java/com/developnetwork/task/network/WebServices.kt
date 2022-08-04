package com.developnetwork.task.network

import com.developnetwork.task.home.model.ProductsModel
import retrofit2.Response
import retrofit2.http.GET

interface WebServices {

    @GET("products")
    suspend fun getAllProducts(): Response<ProductsModel>

}