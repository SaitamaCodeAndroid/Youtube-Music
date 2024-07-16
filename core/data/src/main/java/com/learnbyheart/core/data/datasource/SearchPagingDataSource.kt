package com.learnbyheart.core.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.learnbyheart.core.common.MusicLoadResult
import com.learnbyheart.core.data.repository.search.SearchByTypeRepositoryImpl.Companion.SEARCH_PAGE_SIZE
import com.learnbyheart.core.data.repository.search.SearchByTypeRepositoryImpl.Companion.SEARCH_STARTING_PAGE_INDEX

sealed class SearchPagingDataSource<T : Any>(
    private val loadBlock: suspend (
        index: Int,
        pageSize: Int,
    ) -> MusicLoadResult<T>
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: SEARCH_STARTING_PAGE_INDEX
        return when (val loadResult = loadBlock(position, SEARCH_PAGE_SIZE)) {
            is MusicLoadResult.Success -> LoadResult.Page(
                data = loadResult.data,
                prevKey = if (position == SEARCH_STARTING_PAGE_INDEX) {
                    null
                } else {
                    position + 1
                },
                nextKey = if (loadResult.data.isEmpty()) {
                    null
                } else {
                    position + (params.loadSize / SEARCH_PAGE_SIZE)
                }
            )

            is MusicLoadResult.Error -> LoadResult.Error(
                loadResult.exception
            )
        }
    }
}
