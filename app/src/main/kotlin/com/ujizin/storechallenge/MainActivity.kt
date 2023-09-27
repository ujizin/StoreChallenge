package com.ujizin.storechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ujizin.storechallenge.presentation.StoreChallengeNavigation
import com.ujizin.storechallenge.themes.StoreChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoreChallengeTheme {
                StoreChallengeNavigation()
            }
        }
    }
}
