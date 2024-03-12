package com.learnbyheart.core.nowplaying.component

import android.net.Uri
import androidx.media3.common.MediaItem

interface MediaPlayer {

    fun addMediaUri(uri: Uri)

    fun playMedia(mediaItem: MediaItem)

    fun preparePlayer()

    fun releasePlayer()
}
