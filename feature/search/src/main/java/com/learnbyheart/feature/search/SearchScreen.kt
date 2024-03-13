package com.learnbyheart.feature.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.learnbyheart.core.ui.Black1A1A1A
import com.learnbyheart.core.ui.GreyB9B9B9

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch() = navigate(SEARCH_ROUTE)

fun NavGraphBuilder.searchScreen(
    onBackClick: () -> Unit,
) {
    composable(SEARCH_ROUTE) {
        SearchScreen(onBackClick = onBackClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchScreen(
    onBackClick: () -> Unit,
) {
    var query by remember {
        mutableStateOf("")
    }

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

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
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
            query = query,
            onQueryChange = {
                query = it
            },
            onSearch = {},
            active = false,
            onActiveChange = {},
            content = {}
        )
    }
}

@Preview
@Composable
private fun SearchScreenPreview() {
    SearchScreen {}
}
