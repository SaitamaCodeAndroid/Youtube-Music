package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName
import com.learnbyheart.core.model.PlayList

data class PlaylistMetadata(
    @SerializedName("items")
    val playLists: List<PlayList>,
    val total: Int,
)
