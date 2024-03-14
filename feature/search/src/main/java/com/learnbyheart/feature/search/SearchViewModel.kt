package com.learnbyheart.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.common.asResult
import com.learnbyheart.core.domain.SearchDisplayData
import com.learnbyheart.core.domain.SearchInteractor
import com.learnbyheart.core.network.model.getAllTypeNameAsString
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

private const val SEARCH_TYPE_KEY = "search_type_key"
private const val SEARCH_QUERY_KEY = "search_query_key"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchInteractor: SearchInteractor,
) : ViewModel() {

    internal val searchType = savedStateHandle.getStateFlow(SEARCH_TYPE_KEY, getAllTypeNameAsString())
    internal val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, "")

    private val _searchResultUiState =
        MutableStateFlow<Result<List<SearchDisplayData>>>(Result.Loading)
    val searchResultUiState: StateFlow<Result<List<SearchDisplayData>>> =
        _searchResultUiState.asStateFlow()

    fun onSearchTypeChanged(type: String) {
        savedStateHandle[SEARCH_TYPE_KEY] = type
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = query
    }

    fun onSearchTriggered(
        query: String,
        type: String,
    ) {
        if (query.isNotEmpty() && query != searchQuery.value) {
            searchInteractor(
                searchQuery = query,
                searchType = type
            )
                .asResult()
                .mapLatest { result ->
                    when (result) {
                        is Result.Loading -> {}
                        is Result.Success -> _searchResultUiState.update { Result.Success(result.data) }
                        is Result.Error -> _searchResultUiState.update { Result.Error(result.exception) }
                    }
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(),
                    initialValue = Result.Loading
                )
        }
    }
}
