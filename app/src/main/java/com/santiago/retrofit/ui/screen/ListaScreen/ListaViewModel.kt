package com.santiago.retrofit.ui.screen.ListaScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.santiago.retrofit.data.model.Product
import com.santiago.retrofit.data.repositories.repositoryList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UiState(
    val loading: Boolean = false,
    val products: List<Product>? = null,
    val navigateTo: Int? = null
)

class ListaViewModel : ViewModel() {
    private val _uiState: MutableLiveData<UiState> = MutableLiveData(UiState())
    val lista: LiveData<UiState> = _uiState

    init {
        _uiState.value = _uiState.value?.copy(loading = true)
        requestProducts()
    }

    private fun requestProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val products = repositoryList.getProducts()
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value?.copy(
                        products = products,
                        loading = false
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _uiState.value = _uiState.value?.copy(loading = false)
                }
            }
        }
    }

    fun navigateToDetail(productId: Int) {
        _uiState.value = _uiState.value?.copy(navigateTo = productId)
    }

    fun navigateToDetailDone() {
        _uiState.value = _uiState.value?.copy(navigateTo = null)
    }
}

class ListaViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListaViewModel() as T
    }
} 