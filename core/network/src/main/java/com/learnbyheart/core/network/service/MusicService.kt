package com.learnbyheart.core.network.service

import com.learnbyheart.core.network.model.AlbumResponse
import com.learnbyheart.core.network.model.CategoryResponse
import com.learnbyheart.core.network.model.PlayListResponse
import com.learnbyheart.core.network.model.RecommendationTrackResponse
import com.learnbyheart.core.network.token.AccessTokenResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

private const val API_VERSION = "v1"

interface MusicService {

    @FormUrlEncoded
    @POST("api/token")
    suspend fun getAccessToken(
        @Header("Authorization") authorization: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): AccessTokenResponse

    /**
     * Get recommendations tracks
     */
    @GET("${API_VERSION}/recommendations")
    suspend fun getRecommendationAlbums(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: Int = 10,
        @Query("market") countryCode: String,
        @Query("seed_genres") genres: String,
    ): Response<RecommendationTrackResponse>

    /**
     * Get a list of albums by category
     */
    @GET("${API_VERSION}/browse/categories")
    suspend fun getAlbumsByCategory(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): Response<CategoryResponse>

    /**
     * Get a list of new album releases featured
     */
    @GET("${API_VERSION}/browse/new-releases")
    suspend fun getNewReleaseAlbums(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): Response<AlbumResponse>

    /**
     * Get a list of Spotify featured playlists
     */
    @GET("${API_VERSION}/browse/featured-playlists")
    suspend fun getFeaturedPlaylist(
        @Header("Authorization") authorization: String,
        @Query("locale") locale: String,
    ): Response<PlayListResponse>

}
