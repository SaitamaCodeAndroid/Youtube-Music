package com.learnbyheart.core.network.service

import com.learnbyheart.core.network.model.AlbumResponse
import com.learnbyheart.core.network.model.CategoryResponse
import com.learnbyheart.core.network.model.PlayListResponse
import com.learnbyheart.core.network.model.RecommendationTrackResponse
import com.learnbyheart.core.network.token.AccessTokenResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private const val API_VERSION = "v1"
private const val RECOMMEND_TRACKS_LIMIT = 20
private const val POPULAR_TRACKS_LIMIT = 40

interface MusicService {

    /**
     * Get recommendations tracks
     */
    @GET("${API_VERSION}/recommendations")
    suspend fun getRecommendationTracks(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = RECOMMEND_TRACKS_LIMIT,
        @Query("market") countryCode: String,
        @Query("seed_genres") genres: String,
    ): RecommendationTrackResponse

    /**
     * Get the most listened tracks
     */
    @GET("${API_VERSION}/recommendations")
    suspend fun getPopularTracks(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = POPULAR_TRACKS_LIMIT,
        @Query("target_popularity") popularity: Int = 100,
        @Query("market") countryCode: String,
        @Query("seed_genres") genres: String,
    ): RecommendationTrackResponse

    /**
     * Get a list of music categories
     */
    @GET("${API_VERSION}/browse/categories")
    suspend fun getCategories(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): CategoryResponse

    /**
     * Get a list of new album releases featured
     */
    @GET("${API_VERSION}/browse/new-releases")
    suspend fun getNewReleaseAlbums(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): AlbumResponse

    /**
     * Get a list of Spotify featured playlists
     */
    @GET("${API_VERSION}/browse/featured-playlists")
    suspend fun getFeaturedPlaylist(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): PlayListResponse

}
