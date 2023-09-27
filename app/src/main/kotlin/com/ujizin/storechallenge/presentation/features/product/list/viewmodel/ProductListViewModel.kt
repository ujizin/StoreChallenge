package com.ujizin.storechallenge.presentation.features.product.list.viewmodel

import androidx.lifecycle.ViewModel
import com.ujizin.storechallenge.core.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    productRepository: ProductRepository,
) : ViewModel() {

    val pager = productRepository.getProducts()
}
