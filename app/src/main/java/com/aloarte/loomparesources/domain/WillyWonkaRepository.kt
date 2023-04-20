package com.aloarte.loomparesources.domain

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import kotlinx.coroutines.flow.Flow

interface WillyWonkaRepository {

    suspend fun getOompaLoompas(page:Int): Flow<OompaLoompaContentBo>

    suspend fun getOompaLoompa(id: Int): OompaLoompaBo

}