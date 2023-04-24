package com.aloarte.loomparesources.data

import com.aloarte.loomparesources.data.dto.FavoriteDto
import com.aloarte.loomparesources.data.dto.OompaLoompaDto
import com.aloarte.loomparesources.data.dto.OompaLoompaListDto
import com.aloarte.loomparesources.data.room.ApiSizeEntity
import com.aloarte.loomparesources.data.room.OompaLoompaEntity
import com.aloarte.loomparesources.domain.model.FavoriteBo
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo
import com.google.gson.Gson
import okhttp3.ResponseBody

object Parsers {
    fun parseResponseList(body: ResponseBody?, gson: Gson): OompaLoompaListDto? = try {
        body?.string()?.let {
            if (it.isEmpty()) null
            else gson.fromJson(it, OompaLoompaListDto::class.java)

        }
    } catch (e: Exception) {
        null
    }

    fun parseResponse(body: ResponseBody?, gson: Gson): OompaLoompaDto? = try {
        body?.string()?.let {
            if (it.isEmpty()) null
            else gson.fromJson(it, OompaLoompaDto::class.java)

        }
    } catch (e: Exception) {
        null
    }

    fun OompaLoompaListDto.toContentsBo() = OompaLoompaContentBo(
        current = current,
        results = results.map(::toItemBo),
        total = total
    )

    fun toItemBo(dto: OompaLoompaDto) =
        OompaLoompaBo(
            firstName = dto.first_name,
            lastName = dto.last_name,
            age = dto.age,
            country = dto.country,
            email = dto.email,
            favorite = toFavoriteBo(dto.favorite),
            gender = dto.gender,
            height = dto.height,
            id = dto.id,
            image = dto.image,
            profession = dto.profession,
            quote = dto.quota,
            description = dto.description
        )

    private fun toFavoriteBo(dto: FavoriteDto) =
        FavoriteBo(
            color = dto.color,
            food = dto.food,
            randomString = dto.random_string,
            song = dto.song
        )


    fun OompaLoompaBo.toEntity(detailId: Int?= null) = OompaLoompaEntity(
        firstName = firstName,
        lastName = lastName,
        age = age,
        country = country,
        email = email,
        food = favorite.food,
        color = favorite.color,
        song = favorite.song,
        randomString = favorite.randomString,
        gender = gender,
        height = height,
        id = detailId ?: id,
        image = image,
        profession = profession,
        quote = quote ?: "",
        description = description ?: ""
    )

    fun OompaLoompaEntity.toBo() = OompaLoompaBo(
        firstName = firstName,
        lastName = lastName,
        age = age,
        country = country,
        email = email,
        favorite = FavoriteBo(
            food = food,
            color = color,
            song = song,
            randomString = randomString
        ),
        gender = gender,
        height = height,
        id = id,
        image = image,
        profession = profession,
        quote = quote ?: "",
        description = description ?: ""
    )

    fun Int.toApiSize() = ApiSizeEntity(count = this, id = 0)
    fun ApiSizeEntity.toApiSize() = count

}

