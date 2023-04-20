package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WillyWonkaRepositoryImpl @Inject constructor(private val datasource: WillyWonkaDatasource) :
    WillyWonkaRepository {
    override suspend fun getOompaLoompas(page: Int): Flow<OompaLoompaContentBo> {
        return datasource.getOompaLoompas(page)
    }

    override suspend fun getOompaLoompa(id: Int): OompaLoompaBo {
        TODO("Not yet implemented")
    }

}