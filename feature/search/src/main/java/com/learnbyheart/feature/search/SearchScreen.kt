package com.learnbyheart.feature.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.domain.SearchDisplayData
import com.learnbyheart.core.network.model.getAllTypeNameAsList
import com.learnbyheart.core.ui.Black1A1A1A
import com.learnbyheart.core.ui.CategoryItem
import com.learnbyheart.core.ui.GreyB9B9B9
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
    val searchResultUiState by viewModel.searchResultUiState.collectAsStateWithLifecycle()

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
                    viewModel.onSearchTriggered(
                        query = it,
                        type = searchType
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxSize(),
        ) {

            when (searchResultUiState) {
                is Result.Loading -> LoadingProgress()
                is Result.Success -> {
                    SearchTypeSection(currentType = searchType) {
                        viewModel.onSearchTypeChanged(it)
                        viewModel.onSearchTriggered(
                            query = searchQuery,
                            type = it
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
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
    currentType: String,
    onSearchTypeChanged: (String) -> Unit = {},
) {
    val types = getAllTypeNameAsList()

    Row(
        modifier = Modifier
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
                isSelected = type == currentType
            ) {
                onSearchTypeChanged(it)
            }
        }
    }
}

@Composable
private fun SearchResultSection(
    searchResult: List<SearchDisplayData>,
    onShowAllClicked: () -> Unit
) {


}

@Preview
@Composable
private fun SearchBarSectionPreview() {
    SearchBarSection("")
}

@Preview
@Composable
private fun SearchTypeSectionPreview() {
    SearchTypeSection(currentType = "Track")
}
