package ru.example.caloriecounterapp.ui.screens.product_detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.example.caloriecounterapp.data.OpenFoodFactsApi
import ru.example.caloriecounterapp.data.Product

sealed class ProductState {
    object Loading : ProductState()
    data class Success(val product: Product) : ProductState()
    data class Error(val message: String) : ProductState()
}

class ProductDetailViewModel : ViewModel() {
    private val api = OpenFoodFactsApi.create()
    private val _state = MutableStateFlow<ProductState>(ProductState.Loading)
    val state: StateFlow<ProductState> = _state

    fun fetchProduct(barcode: String) {
        viewModelScope.launch {
            _state.value = ProductState.Loading
            try {
                val response = api.getProduct(barcode)
                if (response.status == 1 && response.product != null) {
                    _state.value = ProductState.Success(response.product)
                } else {
                    _state.value = ProductState.Error("Продукт не найден в базе")
                }
            } catch (e: Exception) {
                Log.e("API_ERROR", "Сбой при получении продукта", e)
                _state.value = ProductState.Error("Ошибка: ${e.localizedMessage}")
            } catch (e: retrofit2.HttpException) {
                if (e.code() == 502 || e.code() == 503) {
                    _state.value =
                        ProductState.Error("Сервер базы продуктов временно перегружен. Попробуйте позже.")
                } else {
                    _state.value = ProductState.Error("Ошибка сервера: ${e.code()}")
                }
            } catch (e: Exception) {
                _state.value = ProductState.Error("Ошибка сети. Проверьте подключение к интернету.")
            }
        }
    }
}