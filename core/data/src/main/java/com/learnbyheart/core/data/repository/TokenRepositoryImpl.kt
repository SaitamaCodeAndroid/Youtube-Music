package com.learnbyheart.core.data.repository

import com.learnbyheart.core.data.datastore.PreferenceDataStore
import com.learnbyheart.core.model.BearerToken
import com.learnbyheart.core.network.service.MusicService
import com.learnbyheart.core.network.token.AccessTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val service: MusicService,
    private val preferenceDataStore: PreferenceDataStore,
) : TokenRepository {

    /**
     * Get a new user access token.
     */
    override fun getNewAccessToken(clientSecret: String): Flow<AccessTokenResponse> = flow {
        val response = service.getAccessToken(clientSecret)
        emit(response)
    }

    /**
     * Get token saved in local.
     */
    override fun getSavedAccessToken(): Flow<BearerToken> {
        return preferenceDataStore.getAccessToken()
    }

    /**
     * Save new access token to local.
     */
    override suspend fun saveAccessToken(bearerToken: BearerToken) {
        preferenceDataStore.saveAccessToken(
            token = bearerToken.value,
            expiredTime = bearerToken.expirationTimeInMillis,
        )
    }
}
