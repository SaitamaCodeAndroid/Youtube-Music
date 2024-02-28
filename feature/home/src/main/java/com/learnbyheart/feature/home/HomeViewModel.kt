package com.learnbyheart.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.learnbyheart.core.common.Result
import com.learnbyheart.core.common.asResult
import com.learnbyheart.core.data.model.HomeDataUiState
import com.learnbyheart.core.domain.GetMusicCategoryInteractor
import com.learnbyheart.core.domain.LoadHomeDataInteractor
import com.learnbyheart.core.model.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMusicCategoryInterractor: GetMusicCategoryInteractor,
    private val loadHomeDataInteractor: LoadHomeDataInteractor,
): ViewModel() {

    private val _categoryUiState = MutableStateFlow<Result<List<Category>>>(Result.Loading)
    val categoryUiState: StateFlow<Result<List<Category>>> = _categoryUiState

    private val _homeDataUiState = MutableStateFlow<Result<List<HomeDataUiState>>>(Result.Loading)
    val homeDataUiState: StateFlow<Result<List<HomeDataUiState>>> = _homeDataUiState

    init {
        loadMusicCategories()
        loadHomeData()
    }

    private fun loadHomeData() {
        viewModelScope.launch {
            loadHomeDataInteractor()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> Unit
                        is Result.Error -> {
                            _homeDataUiState.update {
                                Result.Error(result.exception)
                            }
                        }
                        is Result.Success -> {
                            _homeDataUiState.update {
                                Result.Success(result.data)
                            }
                        }
                    }
                }
        }
    }

    private fun loadMusicCategories() {
        viewModelScope.launch {
            getMusicCategoryInterractor()
                .asResult()
                .collectLatest { result ->
                    when (result) {
                        is Result.Loading -> Unit
                        is Result.Success -> {
                            _categoryUiState.update {
                                Result.Success(result.data)
                            }
                        }
                        is Result.Error -> {
                            _categoryUiState.update {
                                Result.Error(result.exception)
                            }
                        }
                    }
                }
        }
    }
}