package com.aloarte.loomparesources.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aloarte.loomparesources.Constants.PAGE_SIZE
import com.aloarte.loomparesources.data.Parsers.toBo
import com.aloarte.loomparesources.data.Parsers.toEntity
import com.aloarte.loomparesources.data.datasources.OompaLoompaPagingSource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.room.WillyWonkaDao
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import kotlinx.coroutines.delay
import javax.inject.Inject

class WillyWonkaRepositoryImpl @Inject constructor(
    private val datasource: WillyWonkaDatasource,
    private val dao: WillyWonkaDao,
) :
    WillyWonkaRepository {

    override fun getOompaLoompas() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = 10,
            initialLoadSize = 25
        ),
        pagingSourceFactory = {
            OompaLoompaPagingSource(datasource, dao)
        }
    ).flow

    override suspend fun getOompaLoompa(id: Int): OompaLoompaBo? {

        val databaseItem = dao.getOompaLoompa(id)?.toBo()
        //Check not only that the item is not null but if it had the description & quote already
        return if (databaseItem != null && !databaseItem.description.isNullOrEmpty() && !databaseItem.quote.isNullOrEmpty()) {
            databaseItem
        } else {
            //Query the API for the detail of the employee ID
            datasource.getOompaLoompa(id)?.let { oompaLoompaFromApi ->
                //Delay to show smoothly the transition with the lottie animation
                delay(1000)
                //Add the detail to the database
                dao.addOompaLoompa(oompaLoompaFromApi.toEntity(id))
                oompaLoompaFromApi
            } ?: databaseItem
        }
    }

}