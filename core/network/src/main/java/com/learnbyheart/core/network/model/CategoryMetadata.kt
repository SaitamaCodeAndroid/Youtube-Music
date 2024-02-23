package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName
import com.learnbyheart.core.model.Category

data class CategoryMetadata(
    @SerializedName("items")
    val categories: List<Category>,
)
