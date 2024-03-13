package com.learnbyheart.core.data.repository.search

import com.learnbyheart.core.network.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun search(
        token: String,
        query: String,
        type: String,
    ): Flow<SearchResponse>
}
