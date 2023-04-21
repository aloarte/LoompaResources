package com.aloarte.loomparesources.data.datasources

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aloarte.loomparesources.domain.model.OompaLoompaBo

class OompaLoompaPagingSource(
    private val apiDatasource: WillyWonkaDatasource
) : PagingSource<Int, OompaLoompaBo>() {

    override fun getRefreshKey(state: PagingState<Int, OompaLoompaBo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OompaLoompaBo> {
        return try {
            val page = params.key ?: 1
            val response = apiDatasource.getOompaLoompas(page = page)

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == 1) {
                    null
                } else {
                    page.minus(1)
                },
                //End of pagination if the page >= total as there are 20 pages only on the API
                nextKey = if (page >= response.total || response.results.isEmpty()) {
                    null
                } else {
                    page.plus(1)
                },
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
