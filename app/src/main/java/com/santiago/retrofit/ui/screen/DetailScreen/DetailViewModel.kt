package com.santiago.retrofit.ui.screen.DetailScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santiago.retrofit.data.model.Product
import com.santiago.retrofit.data.repositories.RemoteConectection
import kotlinx.coroutines.launch

class DetailViewModel(private val productId: Int) : ViewModel() {
    private val _detail = MutableLiveData<UiState>()
    val detail: LiveData<UiState> = _detail

    init {
        requestProduct()
    }

    private fun requestProduct() {
        viewModelScope.launch {
            try {
                _detail.value = UiState(loading = true)
                val product = RemoteConectection.service.getProduct(productId)
                _detail.value = UiState(product = product)
            } catch (e: Exception) {
                _detail.value = UiState(error = e.message)
            }
        }
    }
}

data class UiState(
    val loading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
) 