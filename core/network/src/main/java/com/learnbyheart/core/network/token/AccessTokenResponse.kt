package com.learnbyheart.core.network.token

import com.google.gson.annotations.SerializedName
import com.learnbyheart.core.model.BearerToken
import java.util.Calendar

data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val secondsUntilExpiration: Int,
) {

    private val timeOfExpiration: Long
        get() {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.SECOND, secondsUntilExpiration)
            return calendar.time.time
        }

    fun toBearerToken(): BearerToken = BearerToken(
        bearerToken = accessToken,
        expirationTimeInMillis = timeOfExpiration
    )
}
