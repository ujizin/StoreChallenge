package com.ujizin.storechallenge.presentation.features.product.detail.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.ujizin.storechallenge.domain.Product
import com.ujizin.storechallenge.presentation.features.product.detail.viewmodel.ProductDetailUiState

class ProductDetailStateProvider : PreviewParameterProvider<ProductDetailUiState> {

    override val values: Sequence<ProductDetailUiState>
        get() = sequenceOf(
            ProductDetailUiState.Loading,
            ProductDetailUiState.Error,
            ProductDetailUiState.Success(product)
        )

    companion object {
        val product = Product(
            id = 1,
            title = "title",
            description = "description",
            imageUrl = "",
            rating = 5.0,
            price = 100.0,
            discountPercentage = 20.0,
            stock = 100,
        )

    }
}
