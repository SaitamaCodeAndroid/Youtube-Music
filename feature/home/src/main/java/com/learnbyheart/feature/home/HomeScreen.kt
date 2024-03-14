package com.learnbyheart.feature.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.data.model.HomeDataUiState
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.ui.CategoryItem
import com.learnbyheart.core.ui.LoadingProgress
import com.learnbyheart.feature.home.component.HomeHeader
import com.learnbyheart.feature.home.component.HorizontalTypeItem
import com.learnbyheart.feature.home.component.VerticalTypeItem

const val HOME_ROUTE = "home_route"
private const val HORIZONTAL_GRID_COUNTS = 4

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen() {
    composable(HOME_ROUTE) {
        val homeViewModel = hiltViewModel<HomeViewModel>()
        HomeScreen(viewModel = homeViewModel)
    }
}

@Composable
private fun HomeScreen(viewModel: HomeViewModel) {
    val categoryUiState by viewModel.categoryUiState.collectAsStateWithLifecycle()
    val homeDataUiState by viewModel.homeDataUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        when (homeDataUiState) {
            is Result.Loading -> LoadingProgress()
            is Result.Error -> Unit
            is Result.Success -> HomeLayoutSection((homeDataUiState as Result.Success).data)
        }

        when (categoryUiState) {
            is Result.Loading, is Result.Error -> Unit
            is Result.Success -> CategorySection(
                (categoryUiState as Result.Success).data.map { it.name }
            )
        }
    }
}

@Composable
private fun CategorySection(categories: List<String>) {
    val scrollState = rememberScrollState(0)
    var selected by remember {
        mutableIntStateOf(0)
    }

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
            .padding(
                top = 21.dp,
                start = 8.dp,
                end = 16.dp,
            )
    ) {
        categories.forEachIndexed { index, item ->
            CategoryItem(
                category = item,
                isSelected = selected == index
            ) {
                selected = index
            }
        }
    }
}

@Composable
private fun HomeLayoutSection(
    uiHomeData: List<HomeDataUiState>
) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(vertical = 70.dp)
    ) {
        uiHomeData.forEach { homeData ->
            when (homeData.musicType) {
                HomeDataType.RECOMMENDATION_TRACK, HomeDataType.POPULAR_TRACK ->
                    SingleSongTypeSection(
                        title = homeData.musicType.header,
                        actionButtonText = stringResource(id = R.string.home_header_button_play_all),
                        homeData.items
                    )

                else -> PlaylistTypeSection(
                    title = homeData.musicType.header,
                    actionButtonText = stringResource(id = R.string.home_header_button_show_more),
                    homeData.items
                )
            }
        }
    }
}

@Composable
private fun PlaylistTypeSection(
    title: String,
    actionButtonText: String,
    items: List<MusicDisplayData>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {

        HomeHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = title,
            actionButtonText = actionButtonText
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .horizontalScroll(rememberScrollState())
        ) {

            items.forEach { playlist ->
                VerticalTypeItem(item = playlist)
            }
        }
    }
}

@Composable
private fun SingleSongTypeSection(
    title: String,
    actionButtonText: String,
    tracks: List<MusicDisplayData>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp)
    ) {

        HomeHeader(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            title = title,
            actionButtonText = actionButtonText
        )

        LazyHorizontalGrid(
            modifier = Modifier
                .padding(top = 16.dp)
                .height(300.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            rows = GridCells.Fixed(HORIZONTAL_GRID_COUNTS)
        ) {

            items(tracks) { track ->
                HorizontalTypeItem(item = track)
            }
        }
    }
}
