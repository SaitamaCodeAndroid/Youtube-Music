package com.learnbyheart.ytmusic.data.remote.token

import android.util.Base64
import com.learnbyheart.spotify.BuildConfig

fun getSpotifyClientSecret(): String {
    val encoderClientSecret = Base64.encodeToString(
        "${BuildConfig.CLIENT_ID}:${BuildConfig.CLIENT_SECRET}".toByteArray(),
        Base64.NO_WRAP
    )
    return "Basic $encoderClientSecret"
}
