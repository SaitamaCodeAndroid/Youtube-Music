package com.learnbyheart.core.network.service

import com.learnbyheart.core.network.model.AlbumResponse
import com.learnbyheart.core.network.model.CategoryResponse
import com.learnbyheart.core.network.model.PlayListResponse
import com.learnbyheart.core.network.model.RecommendationTrackResponse
import com.learnbyheart.core.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

private const val API_VERSION = "v1"

interface MusicService {

    /**
     * Get recommendations tracks
     */
    @GET("${API_VERSION}/recommendations")
    suspend fun getRecommendationTracks(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = 20,
        @Query("market") countryCode: String,
        @Query("seed_genres") genres: String,
    ): RecommendationTrackResponse

    /**
     * Get the most listened tracks
     */
    @GET("${API_VERSION}/recommendations")
    suspend fun getPopularTracks(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = 40,
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

    /**
     * Get a category's playlist
     */
    @GET("${API_VERSION}/browse/categories/{category_id}/playlists")
    suspend fun getPlaylistsByCategory(
        @Header("Authorization") authorization: String,
        @Path("category_id") category: String,
        @Query("limit") limit: Int = 10,
    ): PlayListResponse

    /**
     * Search for track, playlist, artist and album
     */
    @GET("$API_VERSION/search")
    suspend fun search(
        @Header("Authorization") authorization: String,
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("market") countryCode: String,
        @Query("limit") limit: Int = 3,
        @Query("offset") position: Int = 1
    ): SearchResponse

}
