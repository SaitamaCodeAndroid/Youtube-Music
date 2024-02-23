package com.learnbyheart.core.data.repository.token

import com.learnbyheart.core.model.BearerToken
import com.learnbyheart.core.network.token.AccessTokenResponse
import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun getNewAccessToken(clientSecret: String): Flow<AccessTokenResponse>

    fun getSavedAccessToken(): Flow<BearerToken>

    suspend fun saveAccessToken(bearerToken: BearerToken)
}
