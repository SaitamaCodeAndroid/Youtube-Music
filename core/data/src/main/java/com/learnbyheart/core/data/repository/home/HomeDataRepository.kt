package com.learnbyheart.core.data.repository.home

import com.learnbyheart.core.model.Category
import com.learnbyheart.core.network.model.AlbumResponse
import com.learnbyheart.core.network.model.PlayListResponse
import com.learnbyheart.core.network.model.RecommendationTrackResponse
import kotlinx.coroutines.flow.Flow

interface HomeDataRepository {

    suspend fun getRecommendationTracks(token: kotlin.String): Flow<RecommendationTrackResponse>

    suspend fun getPopularTracks(token: kotlin.String): Flow<RecommendationTrackResponse>

    suspend fun getCategories(token: kotlin.String): Flow<List<Category>>

    suspend fun getNewReleaseAlbums(token: kotlin.String,): Flow<AlbumResponse>

    suspend fun getFeaturedPlaylist(token: kotlin.String): Flow<PlayListResponse>

    suspend fun getNewReleasePlaylist(token: kotlin.String): Flow<PlayListResponse>
}
