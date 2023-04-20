package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.domain.OompaLoompaBo
import kotlinx.coroutines.flow.Flow

interface WillyWonkaDatasource {

    fun getOompaLoompas(page:Int): Flow<List<OompaLoompaBo>>


}