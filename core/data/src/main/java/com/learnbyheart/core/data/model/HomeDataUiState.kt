package com.learnbyheart.core.data.model

import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.model.Artist

data class HomeDataUiState(
    val musicType: HomeDataType,
    val items: List<HomeDisplayData>
)

data class HomeDisplayData(
    val id: String,
    val name: String,
    val image: String,
    val artists: String = "",
)
