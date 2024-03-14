package com.learnbyheart.core.model

import java.util.Calendar
import kotlin.String

data class BearerToken(
    val bearerToken: String,
    val expirationTimeInMillis: Long,
) {

    val value get() = "Bearer $bearerToken"

    val isExpired: Boolean
        get() {
            val currentTime = Calendar.getInstance()
            val expiredTime = Calendar.getInstance()
            expiredTime.timeInMillis = expirationTimeInMillis
            return currentTime.after(expiredTime)
        }
}
