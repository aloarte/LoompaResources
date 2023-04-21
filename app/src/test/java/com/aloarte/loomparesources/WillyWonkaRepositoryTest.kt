@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.aloarte.loomparesources

import com.aloarte.loomparesources.data.WillyWonkaRepositoryImpl
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.utils.CoroutinesTestRule
import com.aloarte.loomparesources.utils.TestData.ID
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompa
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WillyWonkaRepositoryTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @MockK
    private lateinit var datasource: WillyWonkaDatasource

    private lateinit var repository: WillyWonkaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
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