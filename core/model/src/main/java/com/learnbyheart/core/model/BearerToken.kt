package com.learnbyheart.core.model

import java.util.Calendar

data class BearerToken(
    private val bearerToken: String,
    val expirationTimeInMillis: Int,
) {

    val value get() = "Bearer $bearerToken"

    val isExpired: Boolean
        get() {
            val currentTime = Calendar.getInstance()
            val expiredTime = currentTime.set(Calendar.MILLISECOND, expirationTimeInMillis)
            return currentTime.before(expiredTime)
        }
}
