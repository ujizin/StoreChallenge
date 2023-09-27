package com.ujizin.storechallenge.presentation.features.product.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujizin.storechallenge.presentation.components.BoxCircularProgressIndicator
import com.ujizin.storechallenge.presentation.features.product.detail.preview.ProductDetailStateProvider
import com.ujizin.storechallenge.presentation.features.product.detail.viewmodel.ProductDetailUiState
import com.ujizin.storechallenge.presentation.features.product.detail.viewmodel.ProductDetailViewModel
import com.ujizin.storechallenge.themes.StoreChallengeTheme

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(viewModel) { viewModel.loadProduct() }

    ProductDetailUI(
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@Composable
private fun ProductDetailUI(
    uiState: ProductDetailUiState,
    onBackClick: () -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        when (uiState) {
            ProductDetailUiState.Loading -> BoxCircularProgressIndicator(Modifier.fillMaxSize())
            ProductDetailUiState.Error -> Unit
            is ProductDetailUiState.Success -> ProductDetailContent(
                product = uiState.product,
                onBackClick = onBackClick
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProductDetailUIPreview(
    @PreviewParameter(ProductDetailStateProvider::class) uiState: ProductDetailUiState,
) {
    StoreChallengeTheme {
        ProductDetailUI(
            uiState = uiState,
            onBackClick = {},
        )
    }
}