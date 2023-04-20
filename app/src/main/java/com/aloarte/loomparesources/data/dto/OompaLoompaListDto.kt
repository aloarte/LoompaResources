package com.aloarte.loomparesources.data.dto

data class OompaLoompaListDto(
    val current: Int,
    val results: List<OompaLoompaDto>,
    val total: Int
)