package com.aloarte.loomparesources.data.datasources

import com.aloarte.loomparesources.Constants.API_SUCCESS_CODE
import com.aloarte.loomparesources.data.Parsers.parseResponse
import com.aloarte.loomparesources.data.Parsers.parseResponseList
import com.aloarte.loomparesources.data.Parsers.toContentsBo
import com.aloarte.loomparesources.data.Parsers.toItemBo
import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.google.gson.Gson
import javax.inject.Inject

class WillyWonkaDatasourceImpl @Inject constructor(
    private val api: WillyWonkaApi,
    private val gson: Gson
) : WillyWonkaDatasource {

    override suspend fun getOompaLoompas(page: Int): OompaLoompaContentBo {
        return try {
            val response = api.getOompaLoompas(page)

            if (response.code() == API_SUCCESS_CODE) {
                val oompaLoompaContents = parseResponseList(response.body(), gson)
                oompaLoompaContents?.toContentsBo() ?: OompaLoompaContentBo()

            } else {
                OompaLoompaContentBo()
            }
        } catch (e: Exception) {
            OompaLoompaContentBo()
        }
    }

    override suspend fun getOompaLoompa(id: Int): OompaLoompaBo? {
        return try {
            val response = api.getOompaLoompaDetail(id)
            if (response.code() == API_SUCCESS_CODE) {
                val oompaLoompaContent = parseResponse(response.body(), gson)
                if (oompaLoompaContent != null) {
                    toItemBo(oompaLoompaContent)
                } else {
                    null
                }

            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

}