package com.learnbyheart.core.domain

import com.learnbyheart.core.data.datasource.MusicDataSource
import com.learnbyheart.core.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMusicCategoryInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val dataSource: MusicDataSource,
) {

    operator fun invoke(): Flow<List<Category>> {
        return getAccessTokenInteractor()
            .map { token ->
                dataSource
                    .getCategories(token = token.value)
                    .categoryMetadata
                    .categories
            }
    }
}
