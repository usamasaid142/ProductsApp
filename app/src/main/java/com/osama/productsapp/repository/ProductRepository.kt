package com.osama.productsapp.repository

import com.osama.productsapp.common.ApiService
import javax.inject.Inject

class ProductRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getAllProducts() = apiService.getAllProducts()
}