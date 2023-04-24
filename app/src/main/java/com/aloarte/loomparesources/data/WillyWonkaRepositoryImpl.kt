package com.aloarte.loomparesources.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.aloarte.loomparesources.data.datasources.OompaLoompaPagingSource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import kotlinx.coroutines.delay
import javax.inject.Inject

class WillyWonkaRepositoryImpl @Inject constructor(private val datasource: WillyWonkaDatasource) :
    WillyWonkaRepository {

    override fun getOompaLoompas() = Pager(
        config = PagingConfig(
            pageSize = 25,
        ),
        pagingSourceFactory = {
            OompaLoompaPagingSource(datasource)
        }
    ).flow

    override suspend fun getOompaLoompa(id: Int): OompaLoompaBo? {
        //Delay to show smoothly the transition with the lottie animation
        delay(1000)
        return datasource.getOompaLoompa(id)
    }

}