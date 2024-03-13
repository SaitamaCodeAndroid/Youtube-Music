package com.learnbyheart.core.data.model

import com.learnbyheart.core.data.HomeDataType
import com.learnbyheart.core.model.MusicDisplayData

data class HomeDataUiState(
    val musicType: HomeDataType,
    val items: List<MusicDisplayData>
)
