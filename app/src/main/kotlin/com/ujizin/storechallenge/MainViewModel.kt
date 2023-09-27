package com.ujizin.storechallenge

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujizin.storechallenge.core.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUIState>(MainUIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun syncProducts() {
        repository
            .syncProducts()
            .onStart { _uiState.update { MainUIState.Loading } }
            .onEach { result ->
                _uiState.update {
                    if (result.isSuccess) MainUIState.Synced else MainUIState.Error
                }
            }
            .launchIn(viewModelScope)
    }
}

sealed interface MainUIState {
    data object Loading : MainUIState
    data object Synced : MainUIState
    data object Error : MainUIState
}