package com.learnbyheart.core.model

import kotlin.String

data class MusicDisplayData(
    val id: String,
    val name: String,
    val image: String,
    val owner: String = "",
)
