package com.learnbyheart.core.data.repository.search

import com.learnbyheart.core.network.model.SearchDisplayData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchAllType(
        token: String,
        query: String,
        type: String,
    ): Flow<SearchDisplayData>
}
