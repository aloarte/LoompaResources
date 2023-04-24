@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.aloarte.loomparesources

import com.aloarte.loomparesources.data.Parsers.toBo
import com.aloarte.loomparesources.data.Parsers.toEntity
import com.aloarte.loomparesources.data.WillyWonkaRepositoryImpl
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.room.WillyWonkaDao
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.utils.CoroutinesTestRule
import com.aloarte.loomparesources.utils.TestData.ID
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompa
import com.aloarte.loomparesources.utils.TestData.detailedOompaLoompaEntity
import com.aloarte.loomparesources.utils.TestData.oompaLoompaEntity
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

    @MockK
    private lateinit var dao: WillyWonkaDao

    private lateinit var repository: WillyWonkaRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = WillyWonkaRepositoryImpl(datasource, dao)
    }

    @Test
    fun `test get oompa loompa detail, item found with extra data`() =
        coroutinesTestRule.runBlockingTest {
            coEvery { dao.getOompaLoompa(employeeId = ID) } returns detailedOompaLoompaEntity

            val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

            coVerify { dao.getOompaLoompa(employeeId = ID) }
            Assert.assertEquals(detailedOompaLoompa, detailResult)
        }

    @Test
    fun `test get oompa loompa detail, item found in ddbb without extra data, call api success`() =
        coroutinesTestRule.runBlockingTest {
            val entityParsedFromApi = detailedOompaLoompa.toEntity(ID)
            coEvery { dao.getOompaLoompa(employeeId = ID) } returns oompaLoompaEntity
            coEvery { datasource.getOompaLoompa(id = ID) } returns detailedOompaLoompa
            coEvery { dao.addOompaLoompa(entityParsedFromApi) } returns 0

            val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

            coVerify { dao.getOompaLoompa(employeeId = ID) }
            coVerify { datasource.getOompaLoompa(id = ID) }
            coVerify { dao.addOompaLoompa(entityParsedFromApi) }
            Assert.assertEquals(detailedOompaLoompa, detailResult)
        }

    @Test
    fun `test get oompa loompa detail, item found in ddbb without extra data, call api error`() =
        coroutinesTestRule.runBlockingTest {
            coEvery { dao.getOompaLoompa(employeeId = ID) } returns oompaLoompaEntity
            coEvery { datasource.getOompaLoompa(id = ID) } returns null

            val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

            coVerify { dao.getOompaLoompa(employeeId = ID) }
            coVerify { datasource.getOompaLoompa(id = ID) }
            coVerify(exactly = 0) { dao.addOompaLoompa(any()) } //The ddbb insertion must be avoided
            Assert.assertEquals(oompaLoompaEntity.toBo(), detailResult)
        }

    @Test
    fun `test get oompa loompa, item not found in ddbb, detail success`() =
        coroutinesTestRule.runBlockingTest {
            val entityParsedFromApi = detailedOompaLoompa.toEntity(ID)
            coEvery { dao.getOompaLoompa(employeeId = ID) } returns null
            coEvery { datasource.getOompaLoompa(id = ID) } returns detailedOompaLoompa
            coEvery { dao.addOompaLoompa(entityParsedFromApi) } returns 0

            val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

            coVerify { dao.getOompaLoompa(employeeId = ID) }
            coVerify { datasource.getOompaLoompa(id = ID) }
            coVerify { dao.addOompaLoompa(entityParsedFromApi) }
            Assert.assertEquals(detailedOompaLoompa, detailResult)
        }

    @Test
    fun `test get oompa loompa, item not found in ddbb, detail error`() =
        coroutinesTestRule.runBlockingTest {
            coEvery { dao.getOompaLoompa(employeeId = ID) } returns null
            coEvery { datasource.getOompaLoompa(id = ID) } returns null

            val detailResult = runBlocking { repository.getOompaLoompa(id = ID) }

            coVerify { dao.getOompaLoompa(employeeId = ID) }
            coVerify { datasource.getOompaLoompa(id = ID) }
            Assert.assertNull(detailResult)
        }
}