package com.ujizin.storechallenge.presentation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ujizin.storechallenge.core.navigation.Argument
import com.ujizin.storechallenge.core.navigation.Screen
import com.ujizin.storechallenge.presentation.features.product.detail.ProductDetailScreen
import com.ujizin.storechallenge.presentation.features.product.list.ProductListScreen

@Composable
fun StoreChallengeNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Screen.ProductList.route) {
        composable(
            route = Screen.ProductList.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { slideOutHorizontally { -it } }
        ) {
            ProductListScreen(onItemClick = { productId ->
                navController.navigate(Screen.ProductDetail.withProductId(productId))
            })
        }
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument(Argument.PRODUCT_ID) { type = NavType.IntType }),
            enterTransition = { slideInHorizontally { it } },
            exitTransition = { slideOutHorizontally { it } }
        ) {
            ProductDetailScreen(onBackClick = { navController.navigateUp() })
        }
    }
}