package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class CategoryMetadata(
    @SerializedName("items")
    val categories: List<Category>,
)
