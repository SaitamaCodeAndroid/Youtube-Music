package com.learnbyheart.core.data.datasource

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import com.learnbyheart.core.common.MusicLoadResult
import com.learnbyheart.core.model.MusicDisplayData
import com.learnbyheart.core.network.service.MusicService
import java.io.IOException

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
class TrackPagingDataSource(
    private val token: String,
    private val query: String,
    private val type: String,
    private val service: MusicService,
    private val countryCode: String = "US",
) : SearchPagingDataSource<MusicDisplayData>(
    loadBlock = { index, pageSize ->
        try {
            val response = service.search(
                authorization = token,
                query = query,
                type = type,
                countryCode = countryCode,
                limit = pageSize,
                position = index
            ).tracks.items.map { it.toMusicDisplayData() }
            MusicLoadResult.Success(response)
        } catch (httpException: HttpException) {
            MusicLoadResult.Error(httpException)
        } catch (ioException: IOException) {
            MusicLoadResult.Error(ioException)
        }
    }
)
