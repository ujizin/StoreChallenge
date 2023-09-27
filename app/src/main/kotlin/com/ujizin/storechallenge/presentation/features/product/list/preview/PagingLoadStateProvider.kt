package com.ujizin.storechallenge.presentation.features.product.list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import com.ujizin.storechallenge.domain.Product

class PagingLoadStateProvider : PreviewParameterProvider<LoadState> {

    override val values: Sequence<LoadState>
        get() = sequenceOf(
            LoadState.Loading,
            LoadState.Error(RuntimeException()),
            LoadState.NotLoading(true)
        )

    companion object {
        // For previewing, unfortunately is not possible for multiple providers
        val products = List(100) {
            Product(
                id = it,
                title = "title $it",
                description = "description $it",
                imageUrl = "",
                rating = (it % 6).toDouble(),
                price = it.toDouble(),
                discountPercentage = 10.0,
                stock = 100
            )
        }
    }
}