package com.ujizin.storechallenge.presentation.features.product.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ujizin.storechallenge.R
import com.ujizin.storechallenge.domain.Product
import com.ujizin.storechallenge.presentation.features.product.detail.preview.ProductDetailStateProvider
import com.ujizin.storechallenge.themes.StoreChallengeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailContent(product: Product, onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                title = {
                    Text(
                        text = product.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1F)
                    .shadow(
                        0.5.dp,
                        ambientColor = MaterialTheme.colorScheme.outline,
                        spotColor = MaterialTheme.colorScheme.outline
                    ),
                model = product.imageUrl,
                contentDescription = product.title,
            )
            ProductDetailInfo(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 32.dp),
                product = product
            )
        }
    }
}

@Preview
@Composable
private fun ProductDetailContentPreview() {
    StoreChallengeTheme {
        ProductDetailContent(
            product = ProductDetailStateProvider.product,
            onBackClick = {}
        )
    }
}