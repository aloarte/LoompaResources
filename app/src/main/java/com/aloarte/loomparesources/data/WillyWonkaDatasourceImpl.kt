package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.domain.OompaLoompaBo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WillyWonkaDatasourceImpl @Inject constructor(private val api: WillyWonkaApi) :
    WillyWonkaDatasource {

    override fun getOompaLoompas(page: Int): Flow<List<OompaLoompaBo>> {
        TODO("Not yet implemented")
    }

}