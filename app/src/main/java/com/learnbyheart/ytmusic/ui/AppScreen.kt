package com.learnbyheart.ytmusic.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.learnbyheart.ytmusic.ui.navigation.TopLevelDestination
import com.learnbyheart.ytmusic.ui.screens.home.homeScreen

@Composable
fun AppScreen(
    appState: AppState = rememberAppState()
) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(
            navController = navController,
            appState = appState,
            ) }
    ) { paddingValue ->

        NavHost(
            modifier = Modifier.padding(paddingValue),
            navController = navController,
            startDestination = TopLevelDestination.Home.route,
        ) {

            homeScreen()
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    appState: AppState,
) {

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    NavigationBar {
        appState.topLevelDestinations.forEachIndexed { index, screen ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {
                    selectedIndex = index
                    navController.navigate(screen.route)
                },
                icon = {
                    if (selectedIndex == index) {
                        painterResource(id = screen.filledIconResource)
                    } else {
                        painterResource(id = screen.outlineIconResource)
                    }
                },
                label = { Text(text = screen.label) }
            )
        }
    }
}
