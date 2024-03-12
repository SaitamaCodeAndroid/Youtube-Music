package com.learnbyheart.core.nowplaying.component

import android.net.Uri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import javax.inject.Inject

class MediaPlayerImpl @Inject constructor(
    private val player: Player
) : MediaPlayer {

    override fun addMediaUri(uri: Uri) {
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    override fun playMedia(mediaItem: MediaItem) {
        player.setMediaItem(mediaItem)
    }

    override fun preparePlayer() {
        player.prepare()
    }

    override fun releasePlayer() {
        player.release()
    }
}
