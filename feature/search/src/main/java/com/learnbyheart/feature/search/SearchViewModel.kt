package com.learnbyheart.feature.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.common.asResult
import com.learnbyheart.core.domain.LoadResultWithTypeInteractor
import com.learnbyheart.core.domain.SearchDataState
import com.learnbyheart.core.domain.SearchInteractor
import com.learnbyheart.core.model.TrackDisplayData
import com.learnbyheart.core.network.model.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val SEARCH_TYPE_KEY = "search_type_key"
private const val SEARCH_QUERY_KEY = "search_query_key"

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val searchInteractor: SearchInteractor,
    private val loadResultWithTypeInteractor: LoadResultWithTypeInteractor,
) : ViewModel() {

    internal val searchType = savedStateHandle.getStateFlow(SEARCH_TYPE_KEY, SearchType.ALL)
    internal val searchQuery = savedStateHandle.getStateFlow(SEARCH_QUERY_KEY, "s")

    private val _searchAllResultUiState =
        MutableStateFlow<Result<List<SearchDataState>>>(Result.Loading)
    val searchAllResultUiState: StateFlow<Result<List<SearchDataState>>> =
        _searchAllResultUiState.asStateFlow()

    val searchTrackResultUiState = loadResultWithTypeInteractor.loadTracks(query = searchQuery.value)

    fun onSearchTypeChanged(type: SearchType) {
        savedStateHandle[SEARCH_TYPE_KEY] = type
    }

    fun onSearchQueryChanged(query: String) {
        savedStateHandle[SEARCH_QUERY_KEY] = query
    }

    fun onSearchAllTriggered(
        query: String,
        type: String,
    ) {
        viewModelScope.launch {
            if (query.isNotEmpty()) {
                onSearchQueryChanged(query)
                onSearchTypeChanged(SearchType.ALL)
                searchInteractor(
                    searchQuery = query,
                    searchType = type
                )
                    .asResult()
                    .collectLatest { result ->
                        when (result) {
                            is Result.Loading -> {}
                            is Result.Success -> _searchAllResultUiState.update { Result.Success(result.data) }
                            is Result.Error -> _searchAllResultUiState.update { Result.Error(result.exception) }
                        }
                    }/*.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(),
                        initialValue = Result.Loading
                    )*/
            }
        }
    }

    fun onSearchTriggered(
        query: String,
        type: SearchType,
    ) {
        when (type) {
            SearchType.TRACK -> {
                loadResultWithTypeInteractor.loadTracks(query = query)

            }
            else -> {}
        }
    }
}
