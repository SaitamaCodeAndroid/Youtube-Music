package com.learnbyheart.ytmusic.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.learnbyheart.core.nowplaying.nowPlayingScreen
import com.learnbyheart.feature.home.homeScreen
import com.learnbyheart.spotify.R
import com.learnbyheart.ytmusic.ui.component.MusicTopAppBar
import com.learnbyheart.ytmusic.ui.navigation.TopLevelDestination
import com.learnbyheart.ytmusic.ui.theme.Grey808080

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    appState: AppState = rememberAppState(),
    viewModel: AppViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            MusicTopAppBar(scrollBehavior) {}
        },
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

            val isConnected by viewModel.isNetworkConnected.collectAsStateWithLifecycle()

            if (!isConnected) {
                NoConnectionSection()
            }

            NavHost(
                navController = navController,
                startDestination = TopLevelDestination.Home.route,
            ) {

                homeScreen()
                nowPlayingScreen()
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController = rememberNavController(),
    appState: AppState = rememberAppState(),
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

@Composable
fun NoConnectionSection() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(70.dp),
            painter = painterResource(id = R.drawable.ic_no_connection),
            contentDescription = "",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = stringResource(id = R.string.no_network_connection)
        )
    }
}

@Preview
@Composable
fun BottomBarPreview() {
    BottomBar()
}

@Preview
@Composable
fun NoConnectionSectionPreview() {
    NoConnectionSection()
}
