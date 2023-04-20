package com.aloarte.loomparesources.domain

import com.aloarte.loomparesources.data.WillyWonkaDatasource
import com.aloarte.loomparesources.data.WillyWonkaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WillyWonkaRepositoryImpl @Inject constructor(private val datasource: WillyWonkaDatasource) :
    WillyWonkaRepository {
    override fun getOompaLoompas(page: Int): Flow<List<OompaLoompaBo>> {
        return datasource.getOompaLoompas(page)
    }

    override fun getOompaLoompa(id: Int): OompaLoompaBo {
        TODO("Not yet implemented")
    }

}