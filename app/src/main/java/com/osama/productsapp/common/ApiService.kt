package com.osama.productsapp.common

import com.osama.productsapp.model.GetProductsRespnse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("products")
    suspend fun getAllProducts():Response<GetProductsRespnse>

}