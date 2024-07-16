package com.learnbyheart.core.data.repository.search

import androidx.paging.PagingData
import com.learnbyheart.core.model.MusicDisplayData
import kotlinx.coroutines.flow.Flow

interface SearchByTypeRepository {

    fun searchTracks(
        token: String,
        query: String,
    ): Flow<PagingData<MusicDisplayData>>
}
