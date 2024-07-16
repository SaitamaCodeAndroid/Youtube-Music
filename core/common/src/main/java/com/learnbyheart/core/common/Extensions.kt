package com.learnbyheart.core.common

fun Int.toManyQuantityStringFormat(): String {
    val result = this / 1000
    return if (result < 1) {
        result.toString()
    } else if (result < 1000) {
        "${result}K"
    } else if (result in 1000 until 1000000) {
        "${result}M"
    } else {
        "${result}B"
    }
}
