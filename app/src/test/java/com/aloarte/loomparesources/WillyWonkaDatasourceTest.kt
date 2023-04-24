package com.aloarte.loomparesources

import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasourceImpl
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.aloarte.loomparesources.utils.CoroutinesTestRule
import com.aloarte.loomparesources.utils.TestData.ID
import com.aloarte.loomparesources.utils.TestData.JSON_DETAIL
import com.aloarte.loomparesources.utils.TestData.JSON_LIST
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompa
import com.aloarte.loomparesources.utils.TestData.oompaLoompa
import com.aloarte.loomparesources.utils.Utils.buildResponse
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WillyWonkaDatasourceTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var api: WillyWonkaApi

    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    private lateinit var datasource: WillyWonkaDatasource

    private var gson: Gson = Gson()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        datasource = WillyWonkaDatasourceImpl(api, gson)
    }

    @Test
    fun `test get oompa loompas success`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.getOompaLoompas(page = 1) } returns
                mediaType.buildResponse(resultCode = 200, json = JSON_LIST)

        val listResult = runBlocking { datasource.getOompaLoompas(page = 1) }

        val expected = OompaLoompaContentBo(1, listOf(oompaLoompa), 2)
        coVerify { api.getOompaLoompas(page = 1) }
        Assert.assertEquals(expected, listResult)
    }

    @Test
    fun `test get oompa loompas error`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.getOompaLoompas(page = 1) } returns mediaType.buildResponse(resultCode = 404)

        val listResult = runBlocking { datasource.getOompaLoompas(page = 1) }

        val expected = OompaLoompaContentBo()
        coVerify { api.getOompaLoompas(page = 1) }
        Assert.assertEquals(expected, listResult)
    }

    @Test
    fun `test get oompa loompa detail success`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.getOompaLoompaDetail(id = ID) } returns
                mediaType.buildResponse(resultCode = 200, json = JSON_DETAIL)

        val detailResult = runBlocking { datasource.getOompaLoompa(id = ID) }

        coVerify { api.getOompaLoompaDetail(id = ID) }
        Assert.assertEquals(detailedOompaLoompa.copy(id = 0), detailResult)
    }

    @Test
    fun `test get oompa loompa detail error`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.getOompaLoompaDetail(id = ID) } returns mediaType.buildResponse(resultCode = 404)

        val detailResult = runBlocking { datasource.getOompaLoompa(id = ID) }

        coVerify { api.getOompaLoompaDetail(id = ID) }
        Assert.assertNull(detailResult)
    }
}