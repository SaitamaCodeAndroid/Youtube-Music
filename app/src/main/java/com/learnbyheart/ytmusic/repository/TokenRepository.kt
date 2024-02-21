package com.learnbyheart.ytmusic.repository

import com.learnbyhear.core.domain.token.BearerToken

interface TokenRepository {

    suspend fun getBearerToken(): com.learnbyhear.core.domain.token.BearerToken
}
