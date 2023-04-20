package com.aloarte.loomparesources.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WillyWonkaApi {

    @GET("napptilus/oompa-loompas")
    suspend fun getOompaLoompas(
        @Query("page") page: Int
    ): Response<ResponseBody>

    @GET("napptilus/oompa-loompas/{id}")
    suspend fun getOompaLoompaDetail(
        @Path("id") id: Int
    ): Response<ResponseBody>
}