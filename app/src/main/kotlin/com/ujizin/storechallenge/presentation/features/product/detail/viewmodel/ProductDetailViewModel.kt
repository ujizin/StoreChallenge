package com.ujizin.storechallenge.presentation.features.product.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.storechallenge.core.navigation.Argument
import com.ujizin.storechallenge.core.data.repository.ProductRepository
import com.ujizin.storechallenge.domain.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val productId: Int = checkNotNull(savedStateHandle[Argument.PRODUCT_ID])

    fun loadProduct() {
        productRepository.getProduct(productId)
            .onEach { result ->
                _uiState.update {
                    when {
                        result.isSuccess -> ProductDetailUiState.Success(result.getOrThrow())
                        else -> ProductDetailUiState.Error
                    }

                }
            }
            .launchIn(viewModelScope)
    }

}

sealed interface ProductDetailUiState {
    data object Loading : ProductDetailUiState
    data object Error : ProductDetailUiState
    data class Success(val product: Product) : ProductDetailUiState
}
