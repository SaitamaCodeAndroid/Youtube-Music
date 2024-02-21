package com.learnbyheart.ytmusic.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.tracing.trace
import com.learnbyheart.ytmusic.ui.navigation.TopLevelDestination
import com.learnbyheart.ytmusic.ui.screens.home.HOME_ROUTE
import com.learnbyheart.ytmusic.ui.screens.home.navigateToHome
import com.learnbyheart.ytmusic.ui.screens.search.navigateToSearch
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
) = remember(
    navController,
    coroutineScope
) {
    AppState(navController = navController)
}

class AppState(
    private val navController: NavHostController,
) {

    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.entries

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME_ROUTE -> TopLevelDestination.Home
            //TopLevelDestination.Search.route -> TopLevelDestination.Search
            else -> null
        }

    /**
     * UI logic for navigating to a top level destination in the app. Top level destinations have
     * only one copy of the destination of the back stack, and save and restore state whenever you
     * navigate to and from it.
     *
     * @param topLevelDestination: The destination the app needs to navigate to.
     */
    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        trace("Navigation: ${topLevelDestination.name}") {
            val topLevelNavOptions = navOptions {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }

            when (topLevelDestination) {
                TopLevelDestination.Home -> navController.navigateToHome(topLevelNavOptions)
                //TopLevelDestination.Search -> navController.navigateToSearch(topLevelNavOptions)
            }
        }
    }
}
