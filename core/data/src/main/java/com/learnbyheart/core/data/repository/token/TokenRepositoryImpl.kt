package com.learnbyheart.core.data.repository.token

import com.learnbyheart.core.data.datastore.PreferenceDataStore
import com.learnbyheart.core.model.BearerToken
import com.learnbyheart.core.network.service.AuthenticationService
import com.learnbyheart.core.network.token.AccessTokenResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val authService: AuthenticationService,
    private val preferenceDataStore: PreferenceDataStore,
) : TokenRepository {

    /**
     * Get a new user access token.
     */
    override fun getNewAccessToken(clientSecret: String): Flow<AccessTokenResponse> = flow {
        val response = authService.getAccessToken(clientSecret)
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
            token = bearerToken.bearerToken,
            expiredTime = bearerToken.expirationTimeInMillis,
        )
    }
}
