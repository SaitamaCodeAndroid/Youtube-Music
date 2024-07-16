package com.learnbyheart.core.data.repository.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.learnbyheart.core.data.datasource.TrackPagingDataSource
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.network.model.SearchType
import com.learnbyheart.core.network.service.MusicService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchByTypeRepositoryImpl @Inject constructor(
    private val service: MusicService
): SearchByTypeRepository {

    companion object {
        const val SEARCH_STARTING_PAGE_INDEX = 1
        const val SEARCH_PAGE_SIZE = 30
        const val PREFETCH_DISTANCE = 20
        private const val SEARCH_MAX_SIZE = 70
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun searchTracks(
        token: String,
        query: String,
    ): Flow<PagingData<MusicDisplayData>> {
        return Pager(
            config = PagingConfig(
                pageSize = SEARCH_PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                maxSize = SEARCH_MAX_SIZE
            ),
            pagingSourceFactory = {
                TrackPagingDataSource(
                    token = token,
                    query = query,
                    type = SearchType.TRACK.typeName,
                    service = service
                )
            }
        ).flow
    }
}
