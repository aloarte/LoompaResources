package com.aloarte.loomparesources.ui

data class UiState(
    val homeRequested:Boolean = false,
    val lasListPageRequested:Int = 0,
    val detailRequested:Boolean = false,
    val oompaLoompaDetailId:Int = -1

)