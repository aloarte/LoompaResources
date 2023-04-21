@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalCoroutinesApi::class)

package com.aloarte.loomparesources


import com.aloarte.loomparesources.Utils.buildResponse
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasource
import com.aloarte.loomparesources.data.datasources.WillyWonkaDatasourceImpl
import com.aloarte.loomparesources.data.api.WillyWonkaApi
import com.aloarte.loomparesources.domain.model.FavoriteBo
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.google.gson.Gson
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WillyWonkaDatasourceTest {

    companion object {
        const val NAME = "Marcy"
        const val LAST_NAME = "Karadzas"
        const val COLOR = "red"
        const val DESCRIPTION = "Description"
        const val QUOTE = "quote"
        const val FOOD = "Chocolat"
        const val RANDOM_STRING = "random"
        const val SONG = "song"
        const val GENDER = "F"
        const val IMAGE = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg"
        const val PROFESSION = "Developer"
        const val EMAIL = "mkaradzas1@visualengin.com"
        const val AGE = 21
        const val COUNTRY = "Loompalandia"
        const val HEIGHT = 99
        const val ID = 1
        const val JSON_LIST = "{\n" +
                "    \"current\": 1,\n" +
                "    \"total\": 2,\n" +
                "    \"results\": [\n" +
                "        {\n" +
                "            \"first_name\": \"$NAME\",\n" +
                "            \"last_name\": \"$LAST_NAME\",\n" +
                "            \"favorite\": {\n" +
                "                \"color\": \"$COLOR\",\n" +
                "                \"food\": \"$FOOD\",\n" +
                "                \"random_string\": \"$RANDOM_STRING\",\n" +
                "                \"song\": \"$SONG\"\n" +
                "            },\n" +
                "            \"gender\": \"$GENDER\",\n" +
                "            \"image\": \"$IMAGE\",\n" +
                "            \"profession\": \"$PROFESSION\",\n" +
                "            \"email\": \"$EMAIL\",\n" +
                "            \"age\": $AGE,\n" +
                "            \"country\": \"$COUNTRY\",\n" +
                "            \"height\": $HEIGHT,\n" +
                "            \"id\": $ID\n" +
                "        }]\n" +
                "}"
        const val JSON_DETAIL = "{\n" +
                "    \"last_name\": \"$LAST_NAME\",\n" +
                "    \"description\": \"$DESCRIPTION\",\n" +
                "    \"image\": \"$IMAGE\",\n" +
                "    \"profession\": \"$PROFESSION\",\n" +
                "    \"quota\": \"$QUOTE\",\n" +
                "    \"height\": $HEIGHT,\n" +
                "    \"first_name\": \"$NAME\",\n" +
                "    \"country\": \"$COUNTRY\",\n" +
                "    \"age\": $AGE,\n" +
                "    \"favorite\": {\n" +
                "        \"color\": \"$COLOR\",\n" +
                "        \"food\": \"$FOOD\",\n" +
                "        \"random_string\": \"$RANDOM_STRING\",\n" +
                "        \"song\": \"$SONG\"\n" +
                "    },\n" +
                "    \"gender\": \"$GENDER\",\n" +
                "    \"email\": \"$EMAIL\"\n" +
                "}"

    }

    private val oompaLoompa = OompaLoompaBo(
        firstName = NAME,
        lastName = LAST_NAME,
        profession = PROFESSION,
        image = IMAGE,
        id = ID,
        height = HEIGHT,
        gender = GENDER,
        country = COUNTRY,
        age = AGE,
        email = EMAIL,
        favorite = FavoriteBo(
            song = SONG,
            color = COLOR,
            randomString = RANDOM_STRING,
            food = FOOD
        )
    )
    private val detailedOompaLoompa = OompaLoompaBo(
        firstName = NAME,
        lastName = LAST_NAME,
        description = DESCRIPTION,
        quote = QUOTE,
        profession = PROFESSION,
        image = IMAGE,
        id = 0,
        height = HEIGHT,
        gender = GENDER,
        country = COUNTRY,
        age = AGE,
        email = EMAIL,
        favorite = FavoriteBo(
            song = SONG,
            color = COLOR,
            randomString = RANDOM_STRING,
            food = FOOD
        )
    )

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private var api: WillyWonkaApi = mockk()

    private val mediaType: MediaType = "application/json; charset=utf-8".toMediaType()

    private lateinit var datasource: WillyWonkaDatasource

    private var gson: Gson = Gson()

    @Before
    fun setUp() {
        MockKAnnotations.init()
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
        Assert.assertEquals(detailedOompaLoompa, detailResult)
    }

    @Test
    fun `test get oompa loompa detail error`() = coroutinesTestRule.runBlockingTest {
        coEvery { api.getOompaLoompaDetail(id = ID) } returns mediaType.buildResponse(resultCode = 404)

        val detailResult = runBlocking { datasource.getOompaLoompa(id = ID) }

        coVerify { api.getOompaLoompaDetail(id = ID) }
        Assert.assertNull(detailResult)
    }
}