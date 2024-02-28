package com.learnbyheart.feature.home

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.common.ui.Black292929
import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.data.model.HomeDataUiState
import com.learnbyheart.core.data.model.HomeDisplayData
import com.learnbyheart.core.model.Category
import com.learnbyheart.feature.home.component.HomeHeader
import com.learnbyheart.feature.home.component.LoadingProgress
import com.learnbyheart.feature.home.component.TrackItem

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
fun HomeScreen(viewModel: HomeViewModel) {
    val categoryUiState by viewModel.categoryUiState.collectAsStateWithLifecycle()
    val homeDataUiState by viewModel.homeDataUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        HomeUiLayout(homeDataUiState = homeDataUiState)

        CategorySection(categoryState = categoryUiState)
    }
}

@Composable
fun HomeUiLayout(
    homeDataUiState: Result<List<HomeDataUiState>>,
) {

    val scrollState = rememberScrollState()

    when (homeDataUiState) {
        is Result.Loading -> LoadingProgress()
        is Result.Error -> Unit
        is Result.Success -> {
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .fillMaxSize()
                    .padding(top = 70.dp)
            ) {
                homeDataUiState.data.forEach { homeData ->
                    when (homeData.musicType) {
                        HomeDataType.RECOMMENDATION_TRACK ->
                            RecommendationTrackSection(
                                title = homeData.musicType.header,
                                actionText = stringResource(id = R.string.home_header_button_play_all),
                                homeData.items
                            )

                        else -> Unit
                    }
                }
            }
        }
    }
}

@Composable
fun CategorySection(
    categoryState: Result<List<Category>>,
    onGenreClick: (String) -> Unit = {},
) {

    val scrollState = rememberScrollState(0)

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
        when (categoryState) {
            is Result.Loading -> Unit
            is Result.Error -> Unit
            is Result.Success -> {
                categoryState.data.forEach { category ->
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(shape = RoundedCornerShape(10.dp))
                            .border(
                                shape = RoundedCornerShape(10.dp),
                                width = 1.dp,
                                color = Black292929
                            )
                            .clickable { onGenreClick(category.id) }
                    ) {

                        Text(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 12.dp
                                ),
                            text = category.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun RecommendationTrackSection(
    title: String,
    actionText: String,
    tracks: List<HomeDisplayData>
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
            actionButtonText = actionText
        )

        LazyHorizontalGrid(
            modifier = Modifier
                .height(400.dp)
                .padding(top = 24.dp),
            rows = GridCells.Fixed(HORIZONTAL_GRID_COUNTS)
        ) {

            items(tracks) { track ->
                TrackItem(track = track)
            }
        }
    }
}


@Preview
@Composable
fun CategorySectionPreview() {
    CategorySection(
        categoryState = Result.Success(
            listOf(
                Category(
                    id = "",
                    icons = emptyList(),
                    name = "For you",
                ),
                Category(
                    id = "",
                    icons = emptyList(),
                    name = "Not for you",
                )
            )
        )
    )
}
