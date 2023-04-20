package com.aloarte.loomparesources.domain.model

data class OompaLoompaBo(
    val firstName: String,
    val lastName: String,
    val favorite: FavoriteBo,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Int,
    val quota: String? = null
)