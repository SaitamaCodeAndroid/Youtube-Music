package com.learnbyheart.core.network.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories")
    val categoryMetadata: CategoryMetadata
)
