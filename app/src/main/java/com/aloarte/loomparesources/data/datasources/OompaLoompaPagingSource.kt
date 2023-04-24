package com.aloarte.loomparesources.data.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.aloarte.loomparesources.Constants.PAGE_SIZE
import com.aloarte.loomparesources.data.Parsers.toApiSize
import com.aloarte.loomparesources.data.Parsers.toBo
import com.aloarte.loomparesources.data.Parsers.toEntity
import com.aloarte.loomparesources.data.room.WillyWonkaDao
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo

class OompaLoompaPagingSource(
    private val apiDatasource: WillyWonkaDatasource,
    private val dao: WillyWonkaDao
) : PagingSource<Int, OompaLoompaBo>() {

    private suspend fun getData(page: Int): OompaLoompaContentBo {
        //Get the database info
        val databaseData = dao.getOompaLoompasPaginated(PAGE_SIZE, (page - 1) * PAGE_SIZE)
        val apiSize = dao.getApiTotal()?.count ?: -1


        //if the data from the page is on the database, just return it
        return if (databaseData.isNotEmpty() && apiSize > 0) {
            OompaLoompaContentBo(
                total = apiSize,
                results = databaseData.map { it.toBo() },
                current = databaseData.size
            )

        } else {
            //Query the api to retrieve the page
            val apiData = apiDatasource.getOompaLoompas(page = page)
            //Add the total items in the API only if it were unset (first call)
            if (apiSize < 0) dao.addApiTotal(apiData.total.toApiSize())
            //Add the data from the page in the database
            dao.addOompaLoompas(apiData.results.map { it.toEntity() })
            apiData

        }
    }

    override fun getRefreshKey(state: PagingState<Int, OompaLoompaBo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OompaLoompaBo> {
        return try {
            val page = params.key ?: 1
            val response = getData(page)
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
