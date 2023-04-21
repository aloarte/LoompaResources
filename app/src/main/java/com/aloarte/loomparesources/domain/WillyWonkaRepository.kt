package com.aloarte.loomparesources.domain

import androidx.paging.PagingData
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import kotlinx.coroutines.flow.Flow

interface WillyWonkaRepository {

    //This is not a suspend function since the PagingDatasource launch in a corroutine scope
    fun getOompaLoompas(): Flow<PagingData<OompaLoompaBo>>

    suspend fun getOompaLoompa(id: Int): OompaLoompaBo

}