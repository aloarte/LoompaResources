package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import kotlinx.coroutines.flow.Flow

interface WillyWonkaDatasource {

    suspend fun getOompaLoompas(page:Int): Flow<OompaLoompaContentBo>

    suspend fun getOompaLoompa(id:Int): OompaLoompaBo?

}