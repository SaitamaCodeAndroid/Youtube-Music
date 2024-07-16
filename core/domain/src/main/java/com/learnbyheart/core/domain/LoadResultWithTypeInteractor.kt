package com.learnbyheart.core.domain

import androidx.paging.PagingData
import com.learnbyheart.core.data.repository.search.SearchByTypeRepository
import com.learnbyheart.core.model.MusicDisplayData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class LoadResultWithTypeInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val searchByTypeRepository: SearchByTypeRepository
) {

    fun loadTracks(query: String): Flow<PagingData<MusicDisplayData>> {
        return getAccessTokenInteractor().flatMapLatest {
            searchByTypeRepository.searchTracks(
                token = it.value,
                query = query
            )
        }
    }
}
