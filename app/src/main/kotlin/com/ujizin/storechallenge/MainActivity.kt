package com.ujizin.storechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ujizin.storechallenge.presentation.StoreChallengeNavigation
import com.ujizin.storechallenge.presentation.components.BoxCircularProgressIndicator
import com.ujizin.storechallenge.presentation.components.ErrorMessage
import com.ujizin.storechallenge.themes.StoreChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreChallengeTheme {
                val uiState by viewModel.uiState.collectAsState()

                LaunchedEffect(viewModel) { viewModel.syncProducts() }

                when (uiState) {
                    MainUIState.Error -> ErrorMessage(
                        modifier = Modifier.fillMaxSize(),
                        description = stringResource(id = R.string.error_sync_products_description),
                        retry = viewModel::syncProducts
                    )

                    MainUIState.Loading -> BoxCircularProgressIndicator(Modifier.fillMaxSize())
                    MainUIState.Synced -> StoreChallengeNavigation()
                }

            }
        }
    }
}
