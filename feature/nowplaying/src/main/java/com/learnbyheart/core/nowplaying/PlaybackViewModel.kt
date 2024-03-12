package com.learnbyheart.core.nowplaying

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.learnbyheart.core.nowplaying.component.MediaPlayer
import com.learnbyheart.core.nowplaying.model.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val VIDEO_URI_KEY = "videoUris"

@HiltViewModel
class PlaybackViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    //private val mediaPlayer: MediaPlayer
): ViewModel() {

    private val videoUris = savedStateHandle.getStateFlow(VIDEO_URI_KEY, emptyList<Uri>())

    val videoItems = videoUris.map { uris ->
        uris.map { uri ->
            VideoItem(
                contentUri = uri,
                mediaItem = MediaItem.fromUri(uri),
                name = "Name"
            )
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    /*init {
        mediaPlayer.preparePlayer()
    }

    fun addMediaUri(uri: Uri) {
        savedStateHandle[VIDEO_URI_KEY] = videoUris.value + uri
        mediaPlayer.addMediaUri(uri)
    }

    fun playMediaUri(uri: Uri) {
        val mediaItem = videoItems.value.find {
            it.contentUri == uri
        }?.mediaItem ?: return
        mediaPlayer.playMedia(mediaItem)
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }*/
}
