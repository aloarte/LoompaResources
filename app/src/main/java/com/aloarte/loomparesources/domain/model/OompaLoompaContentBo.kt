package com.aloarte.loomparesources.domain.model


data class OompaLoompaContentBo(
    val current: Int = -1,
    val results: List<OompaLoompaBo> = emptyList(),
    val total: Int = -1
)