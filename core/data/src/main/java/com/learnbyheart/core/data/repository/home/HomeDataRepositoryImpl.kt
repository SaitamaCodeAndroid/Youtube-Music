package com.learnbyheart.core.data.repository.home

import com.learnbyheart.core.data.datasource.MusicDataSource
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeDataRepositoryImpl @Inject constructor(
    private val dataSource: MusicDataSource
) : HomeDataRepository {

    private val genres = listOf(
        "acoustic",
        "country",
        "electronic",
        "k-pop",
        "pop"
    )

    override suspend fun getCategories(token: String) = flow {
        val response = dataSource.getCategories(
            token = token,
        )
        emit(response.categoryMetadata.categories)
    }

    override suspend fun getRecommendationTracks(
        token: String,
    ) = flow {
        val response = dataSource.getRecommendationTracks(
            token = token,
            genres = genres.joinToString(","),
        )
        emit(response)
    }

    override suspend fun getPopularTracks(
        token: String,
    ) = flow {
        val response = dataSource.getPopularTracks(
            token = token,
            genres = genres.joinToString(","),
        )
        emit(response)
    }

    override suspend fun getNewReleaseAlbums(token: String) = flow {
        val response = dataSource.getNewReleaseAlbums(
            token = token,
        )
        emit(response)
    }

    override suspend fun getFeaturedPlaylist(
        token: String,
    ) = flow {
        val response = dataSource.getFeaturedPlaylist(
            token = token,
        )
        emit(response)
    }

    override suspend fun getNewReleasePlaylist(
        token: String,
    ) = flow {
        val response = dataSource.getPlaylistsByCategory(
            token = token,
            category = "new-releases"
        )
        emit(response)
    }

}
