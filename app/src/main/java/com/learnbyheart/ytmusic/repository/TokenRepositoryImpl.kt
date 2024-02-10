package com.learnbyheart.ytmusic.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.learnbyheart.ytmusic.data.remote.service.AuthenticationService
import com.learnbyheart.ytmusic.data.remote.token.BearerToken
import com.learnbyheart.ytmusic.data.remote.token.getSpotifyClientSecret
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val authenticationService: AuthenticationService
) : TokenRepository {

    private var token: BearerToken? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getBearerToken(): BearerToken {
        if (token == null || token?.isExpired == true) {
            getAndAssignToken()
        }
        return token!!
    }

    /**
     * A helper function that gets and assigns a new [token].
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun getAndAssignToken() {
        val clientSecret = getSpotifyClientSecret()
        token = authenticationService
            .getAccessToken(clientSecret)
            .toBearerToken()
    }
}