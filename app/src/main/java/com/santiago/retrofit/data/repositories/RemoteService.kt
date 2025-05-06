package com.santiago.retrofit.data.repositories

import com.santiago.retrofit.data.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface RemoteService {
    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product
} 