package com.learnbyheart.ytmusic.data.remote.token

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class AccessTokenResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val secondsUntilExpiration: Int,
) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun toBearerToken(): BearerToken = BearerToken(
        bearerToken = accessToken,
        timeOfCreation = LocalDateTime.now(),
        secondsUntilExpiration = secondsUntilExpiration
    )
}
