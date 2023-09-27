package com.ujizin.storechallenge.presentation.features.product.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.ujizin.storechallenge.R
import com.ujizin.storechallenge.domain.Product
import com.ujizin.storechallenge.presentation.components.BoxCircularProgressIndicator
import com.ujizin.storechallenge.presentation.components.ErrorMessage
import com.ujizin.storechallenge.presentation.extensions.toCapitalize
import com.ujizin.storechallenge.presentation.features.product.list.components.ProductItem
import com.ujizin.storechallenge.presentation.features.product.list.preview.PagingLoadStateProvider
import com.ujizin.storechallenge.presentation.features.product.list.viewmodel.ProductListViewModel
import com.ujizin.storechallenge.themes.StoreChallengeTheme
import kotlinx.coroutines.flow.MutableStateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    viewModel: ProductListViewModel = hiltViewModel(),
    onItemClick: (id: Int) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagingItems = viewModel.pager.collectAsLazyPagingItems()
    ProductListUI(
        scrollBehavior = scrollBehavior,
        products = pagingItems,
        loadState = pagingItems.loadState.refresh,
        onErrorRetry = { pagingItems.retry() },
        onItemClick = onItemClick
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ProductListUI(
    scrollBehavior: TopAppBarScrollBehavior,
    products: LazyPagingItems<Product>,
    loadState: LoadState,
    onErrorRetry: () -> Unit,
    onItemClick: (id: Int) -> Unit
) {

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.store_title).toCapitalize()) },
            scrollBehavior = scrollBehavior,
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            when (loadState) {
                is LoadState.Error -> ErrorMessage(
                    modifier = Modifier.fillMaxSize(),
                    description = stringResource(id = R.string.error_load_products_description),
                    retry = onErrorRetry,
                )

                LoadState.Loading -> BoxCircularProgressIndicator(Modifier.fillMaxSize())
                is LoadState.NotLoading -> ProductList(
                    products = products,
                    onItemClick = onItemClick,
                )
            }
        }
    }
}

@Composable
fun ProductList(products: LazyPagingItems<Product>, onItemClick: (id: Int) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(count = products.itemCount, key = products.itemKey { it.id }) { index ->
            val product = remember(index) { products[index] } ?: return@items
            ProductItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onItemClick(product.id) },
                title = product.title,
                imageUrl = product.imageUrl,
                rating = product.rating
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true)
@Composable
private fun ProductListUIPreview(
    @PreviewParameter(PagingLoadStateProvider::class) loadState: LoadState,
) {
    StoreChallengeTheme {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
        val products = MutableStateFlow(
            PagingData.from(PagingLoadStateProvider.products)
        ).collectAsLazyPagingItems()
        ProductListUI(
            scrollBehavior = scrollBehavior,
            loadState = loadState,
            products = products,
            onErrorRetry = { products.retry() },
            onItemClick = {}
        )
    }
}