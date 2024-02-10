package com.learnbyheart.ytmusic.ui.navigation

import com.learnbyheart.spotify.R

/**
 * Type for the top level destinations in the application. Each of these destinations
 * can contain one or more screens (based on the window size). Navigation from one screen to the
 * next within a single destination will be handled directly in composables.
 */

enum class TopLevelDestination(
    val route: String,
    val label: String,
    val outlineIconResource: Int,
    val filledIconResource: Int,
) {

    Home(
        route = "home_route",
        label = "Home",
        outlineIconResource = R.drawable.ic_outline_home,
        filledIconResource = R.drawable.ic_filled_home,
    ),

    Search(
        route = "search_route",
        label = "Search",
        outlineIconResource = R.drawable.ic_search,
        filledIconResource = R.drawable.ic_search,
    )
}
