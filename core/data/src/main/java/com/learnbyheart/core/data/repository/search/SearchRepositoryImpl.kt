package com.learnbyheart.core.data.repository.search

import com.learnbyheart.core.data.datasource.MusicDataSource
import com.learnbyheart.core.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val dataSource: MusicDataSource
) : SearchRepository {

    override fun search(
        token: String,
        query: String,
        type: String
    ): Flow<SearchResponse> = flow {
        val response = dataSource.search(
            token = token,
            query = query,
            type = type
        )
        emit(response)
    }
}
