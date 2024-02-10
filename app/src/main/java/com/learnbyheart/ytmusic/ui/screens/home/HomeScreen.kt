package com.learnbyheart.ytmusic.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.learnbyheart.ytmusic.ui.navigation.TopLevelDestination

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}

@Composable
fun HomeScreen() {

}