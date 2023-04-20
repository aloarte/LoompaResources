package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.domain.OompaLoompaBo
import kotlinx.coroutines.flow.Flow

interface WillyWonkaRepository {

    fun getOompaLoompas(page:Int): Flow<List<OompaLoompaBo>>

    fun getOompaLoompa(id: Int): OompaLoompaBo

}