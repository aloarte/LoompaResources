package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.data.Parsers.parseResponse
import com.aloarte.loomparesources.data.Parsers.toContentsBo
import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WillyWonkaDatasourceImpl @Inject constructor(
    private val api: WillyWonkaApi,
    private val gson: Gson
) : WillyWonkaDatasource {
    private val API_SUCCESS_CODE = 200


    override suspend fun getOompaLoompas(page: Int): Flow<OompaLoompaContentBo> {
        return try {
            val response = api.getOompaLoompas(page)

            if (response.code() == API_SUCCESS_CODE) {
                val oompaLompaContents = parseResponse(response.body(), gson)
                if (oompaLompaContents != null) {
                    flow {
                        val a = toContentsBo(oompaLompaContents)
                        emit(a)
                    }
                } else {
                    flow { emit(OompaLoompaContentBo()) }
                }

            } else {
                flow { emit(OompaLoompaContentBo()) }
            }
        } catch (e: Exception) {
            flow { emit(OompaLoompaContentBo()) }
        }
    }


}