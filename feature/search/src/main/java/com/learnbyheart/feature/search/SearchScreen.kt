package com.learnbyheart.feature.search

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.common.toManyQuantityStringFormat
import com.learnbyheart.core.domain.SearchDataState
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.network.model.SearchType
import com.learnbyheart.core.network.model.getAllTypeNameAsList
import com.learnbyheart.core.network.model.getAllTypeNameAsString
import com.learnbyheart.core.ui.Black1A1A1A
import com.learnbyheart.core.ui.CategoryItem
import com.learnbyheart.core.ui.Grey898989
import com.learnbyheart.core.ui.GreyB9B9B9
import com.learnbyheart.core.ui.HeaderSection
import com.learnbyheart.core.ui.LoadingProgress

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
) {
    composable(SEARCH_ROUTE) {
        SearchScreen(onBackClick = onBackClick)
    }
}

@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {

    val searchType by viewModel.searchType.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val searchAllResultUiState by viewModel.searchAllResultUiState.collectAsStateWithLifecycle()
    val searchTrackResultUiState = viewModel.searchTrackResultUiState.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = { onBackClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            SearchBarSection(
                searchQuery = searchQuery,
                onSearchQueryChanged = viewModel::onSearchQueryChanged,
                onSearchTriggered = {
                    viewModel.onSearchAllTriggered(
                        query = it,
                        type = searchType.displayName
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize(),
        ) {

            when (searchAllResultUiState) {
                is Result.Loading -> LoadingProgress()
                is Result.Success -> {
                    SearchTypeSection(currentType = searchType) {
                        viewModel.onSearchTypeChanged(searchType)
                        viewModel.onSearchAllTriggered(
                            query = searchQuery,
                            type = ""
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(top = 12.dp))

                    when (searchType) {
                        SearchType.ALL -> SearchAllTypeResultSection(searchResultData = (searchAllResultUiState as Result.Success).data) { }
                        SearchType.TRACK -> SearchByTypeResultSection(
                            type = SearchType.TRACK,
                            searchResultData = searchTrackResultUiState
                        ) {}

                        else -> {}
                        /*SearchType.ALBUM -> SearchByTypeResultSection(searchResultData = (searchAllResultUiState as Result.Success).data[1]) {}
                        SearchType.ARTIST -> SearchByTypeResultSection(searchResultData = (searchAllResultUiState as Result.Success).data[2]) {}
                        SearchType.PLAYLIST -> SearchByTypeResultSection(searchResultData = (searchAllResultUiState as Result.Success).data[3]) {}*/
                    }
                }

                is Result.Error -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBarSection(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {}
) {
    SearchBar(
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(
                    onClick = { onSearchQueryChanged("") }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_clear),
                        contentDescription = null,
                    )
                }
            }
        },
        colors = SearchBarDefaults.colors(
            containerColor = Black1A1A1A
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.search_bar_playholder),
                fontSize = 14.sp,
                color = GreyB9B9B9,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        query = searchQuery,
        onQueryChange = { onSearchQueryChanged(it) },
        onSearch = { onSearchTriggered(it) },
        active = false,
        onActiveChange = { /* Do nothing */ }
    ) {

    }
}

@Composable
private fun SearchTypeSection(
    currentType: SearchType,
    onSearchTypeChanged: (SearchType) -> Unit = {},
) {

    val scrollState = rememberScrollState()
    val types = getAllTypeNameAsList()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 16.dp,
                end = 16.dp
            )
    ) {
        types.forEach { type ->
            CategoryItem(
                category = type,
                isSelected = type == currentType.typeName
            ) {
                val searchType = if (currentType == SearchType.ALL) {
                    getAllTypeNameAsString()
                } else {
                    it
                }
                onSearchTypeChanged(currentType)
            }
        }
    }
}

@Composable
private fun SearchAllTypeResultSection(
    searchResultData: List<SearchDataState>,
    onShowAllClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize()
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
    ) {
        searchResultData.forEach { searchData ->
            HeaderSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                title = searchData.type.displayName.replaceFirstChar { it.titlecase() },
                actionButtonText = stringResource(id = R.string.search_result_header_button_show_more)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                searchData.data.forEach { item ->
                    SearchDataItem(
                        searchType = searchData.type,
                        searchItem = item
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchByTypeResultSection(
    type: SearchType,
    searchResultData: LazyPagingItems<MusicDisplayData>,
    onItemClicked: (String) -> Unit = {}
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp)
    ) {
        items(
            searchResultData.itemCount,
            searchResultData.itemKey { it }
        ) { index ->
            SearchDataItem(
                searchType = type,
                searchItem = searchResultData[index]!!
            )
        }
    }
}

@Composable
fun SearchDataItem(
    searchType: SearchType,
    searchItem: MusicDisplayData
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(4.dp)),
            model = searchItem.image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = com.learnbyheart.core.ui.R.drawable.img_music_thumbnail)
        )

        Column(
            modifier = Modifier.padding(start = 12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val description = when (searchType) {
                SearchType.TRACK -> stringResource(
                    R.string.search_result_track_description_text,
                    searchItem.owner,
                    searchItem.duration
                )

                SearchType.ALBUM -> stringResource(
                    R.string.search_result_album_description_text,
                    searchItem.albumType.typeName,
                    searchItem.owner
                )

                SearchType.ARTIST -> pluralStringResource(
                    R.plurals.search_result_artist_description_text,
                    searchItem.totalSubscriber,
                    searchItem.totalSubscriber
                )

                SearchType.PLAYLIST -> stringResource(
                    R.string.search_result_playlist_description_text,
                    searchItem.owner,
                    searchItem.totalTrack.toManyQuantityStringFormat()
                )

                SearchType.ALL -> ""
            }

            Text(
                textAlign = TextAlign.Start,
                text = searchItem.name,
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = description,
                color = Grey898989,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.size(10.dp),
            onClick = { /*TODO*/ }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarSectionPreview() {
    SearchBarSection("")
}

@Preview
@Composable
private fun SearchTypeSectionPreview() {
    SearchTypeSection(currentType = SearchType.TRACK)
}

@Preview
@Composable
private fun SearchDataItemPreview() {
    SearchDataItem(
        searchType = SearchType.TRACK,
        searchItem = MusicDisplayData(
            id = "",
            name = "Ghost",
            image = "",
            owner = "Justin Bieber"
        )
    )
}
