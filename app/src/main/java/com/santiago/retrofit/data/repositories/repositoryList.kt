package com.santiago.retrofit.data.repositories

import com.santiago.retrofit.data.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object repositoryList {
    suspend fun getProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            RemoteConectecition.service.getProducts()
        }
    }
} 