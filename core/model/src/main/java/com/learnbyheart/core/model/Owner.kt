package com.learnbyheart.core.model

import com.google.gson.annotations.SerializedName
import kotlin.String

data class Owner(
    val id: String,
    @SerializedName("display_name")
    val name: String,
)