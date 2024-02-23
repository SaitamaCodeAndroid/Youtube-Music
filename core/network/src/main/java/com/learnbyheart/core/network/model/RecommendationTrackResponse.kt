package com.learnbyheart.core.network.model

import com.learnbyheart.core.model.Track

data class RecommendationTrackResponse(
    val tracks: List<Track>,
)
