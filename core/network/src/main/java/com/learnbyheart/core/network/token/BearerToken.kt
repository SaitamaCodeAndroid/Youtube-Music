package com.learnbyheart.core.network.token

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class BearerToken(
    private val bearerToken: String,
    val timeOfCreation: LocalDateTime,
    val secondsUntilExpiration: Int
) {

    val value get() = "Bearer $bearerToken"

    val isExpired: Boolean
        @RequiresApi(Build.VERSION_CODES.O)
        get() {
            val timeOfExpiration = timeOfCreation.plusSeconds(secondsUntilExpiration.toLong())
            return LocalDateTime.now() > timeOfExpiration
        }
}
