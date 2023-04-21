@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.aloarte.loomparesources


import com.aloarte.loomparesources.data.WillyWonkaRepositoryImpl
import com.aloarte.loomparesources.utils.Utils.buildResponse
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasourceImpl
import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.FavoriteBo
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.aloarte.loomparesources.utils.CoroutinesTestRule
import com.aloarte.loomparesources.utils.TestData.ID
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompa
import com.aloarte.loomparesources.utils.TestData.oompaLoompa
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WillyWonkaRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private var datasource: WillyWonkaDatasource = mockk()

    private lateinit var repository: WillyWonkaRepository


    @Before
    fun setUp() {
        MockKAnnotations.init()
        repository = WillyWonkaRepositoryImpl(datasource)
    }


    @Test
    fun `test get oompa loompa detail success`() = coroutinesTestRule.runBlockingTest {
        coEvery { datasource.getOompaLoompa(id = ID) } returns detailedOompaLoompa

        val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

        coVerify { datasource.getOompaLoompa(id = ID) }
        Assert.assertEquals(detailedOompaLoompa, detailResult)
    }

    @Test
    fun `test get oompa loompa detail error`() = coroutinesTestRule.runBlockingTest {
        coEvery { datasource.getOompaLoompa(id = ID) } returns null

        val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

        coVerify { datasource.getOompaLoompa(id = ID) }
        Assert.assertNull(detailResult)
    }
}