package com.learnbyheart.core.domain

import com.learnbyheart.core.data.repository.home.HomeDataRepositoryImpl
import com.learnbyheart.core.model.Category
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

class GetMusicCategoryInteractor @Inject constructor(
    private val getAccessTokenInteractor: GetAccessTokenInteractor,
    private val homeDataRepositoryImpl: HomeDataRepositoryImpl,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<List<Category>> {
        return getAccessTokenInteractor()
            .flatMapLatest { token ->
                homeDataRepositoryImpl.getCategories(token = token.value)
            }
    }
}
