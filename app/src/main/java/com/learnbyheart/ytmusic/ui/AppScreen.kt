package com.learnbyheart.ytmusic.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.learnbyheart.ytmusic.ui.component.MusicTopAppBar
import com.learnbyheart.ytmusic.ui.navigation.TopLevelDestination
import com.learnbyheart.ytmusic.ui.screens.home.homeScreen
import com.learnbyheart.ytmusic.ui.theme.Grey808080

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    appState: AppState = rememberAppState()
) {
    val navController = rememberNavController()

    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {MusicTopAppBar {

        }},
        bottomBar = {
            BottomBar(
                navController = navController,
                appState = appState,
            )
        }
    ) { paddingValue ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .consumeWindowInsets(paddingValue)
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal,
                    ),
                ),
        ) {

            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.Home.route,
            ) {

                homeScreen()
            }
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

    NavigationBar(
        containerColor = Grey808080
    ) {
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
