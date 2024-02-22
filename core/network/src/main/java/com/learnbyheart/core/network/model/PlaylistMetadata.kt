package com.learnbyheart.core.network.model

import com.learnbyheart.core.model.PlayList

data class PlaylistMetadata(
    val playLists: List<PlayList>,
    val total: Int,
)
