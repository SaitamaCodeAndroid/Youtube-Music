package com.learnbyheart.ytmusic.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.learnbyheart.ytmusic.ui.theme.Grey808080
import com.learnbyheart.ytmusic.ui.theme.GreyEDEDEDx

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(HOME_ROUTE) {
        HomeScreen()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        stickyHeader {
            GenreList(genres = listOf())
        }
    }
}

@Composable
fun GenreList(genres: List<String>) {
    Row(
        modifier = Modifier
            .padding(
                top = 16.dp,
                start = 8.dp,
                end = 16.dp
            )
            .horizontalScroll(state = ScrollState(0))
    ) {
        genres.forEach {genreName ->
            MusicGenre(name = genreName) {}
        }
    }
}

@Composable
fun MusicGenre(
    name: String,
    onGenreClick: () -> Unit,
) {
    Box (
        modifier = Modifier
            .border(
                shape = RoundedCornerShape(10.dp),
                width = 1.dp,
                color = Grey808080
            )
            .padding(start = 8.dp)
            .background(
                shape = RoundedCornerShape(10.dp),
                color = GreyEDEDEDx
            ),
    ){
        Text(
            text = name,
            style = TextStyle(
                color = Color.White
            )
        )
    }
}

@Preview
@Composable
fun GenreListPreview() {
    GenreList(genres = listOf("Energize"))
}
