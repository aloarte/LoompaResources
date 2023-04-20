package com.aloarte.loomparesources.data.dto

data class OompaLoompaDto(
    val age: Int,
    val country: String,
    val quota: String?=null,
    val description: String?=null,
    val email: String,
    val favorite: FavoriteDto,
    val first_name: String,
    val gender: String,
    val height: Int,
    val id: Int=-1,
    val image: String,
    val last_name: String,
    val profession: String
)