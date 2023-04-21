package com.aloarte.loomparesources.ui

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.ui.state.ScreenStatus

data class UiState(
    val homeRequested: ScreenStatus = ScreenStatus.NotRequested,
    val detailRequested: ScreenStatus = ScreenStatus.NotRequested,
    val errorMessage: String? = null,
    val oompaLoompaDetailId: Int = -1,
    val oompaLoompaDetail: OompaLoompaBo? = null


)