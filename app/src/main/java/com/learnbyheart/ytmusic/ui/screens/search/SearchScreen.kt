package com.learnbyheart.ytmusic.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions) = navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen() {
    composable(SEARCH_ROUTE) {
        SearchScreen()
    }
}

@Composable
fun SearchScreen() {
    TODO("Not yet implemented")
}
