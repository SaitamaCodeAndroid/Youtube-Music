package com.learnbyheart.core.domain

import android.util.Base64
import com.learnbyhear.core.domain.BuildConfig
import com.learnbyheart.core.data.repository.token.TokenRepository
import com.learnbyheart.core.model.BearerToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GetAccessTokenInteractor @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    operator fun invoke(): Flow<BearerToken> = tokenRepository
        .getSavedAccessToken()
        .flatMapLatest { token ->
            if (token.isExpired) {
                tokenRepository.getNewAccessToken(getSpotifyClientSecret())
                    .map {
                        it.toBearerToken()
                    }
            } else {
                flowOf(token)
            }
        }

    private fun getSpotifyClientSecret(): String {
        val encoderClientSecret = Base64.encodeToString(
            "${BuildConfig.CLIENT_ID}:${BuildConfig.CLIENT_SECRET}".toByteArray(),
            Base64.NO_WRAP
        )
        return "Basic $encoderClientSecret"
    }
}
