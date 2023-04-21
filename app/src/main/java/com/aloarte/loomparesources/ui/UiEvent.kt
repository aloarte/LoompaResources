package com.aloarte.loomparesources.ui

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo

sealed interface UiEvent {
    object LoadHome : UiEvent
    data class LoadDetail(val oompaLoompaId:Int) : UiEvent
    data class OompaLoompaDetailSuccess(val oompaLoompa: OompaLoompaBo) : UiEvent
    data class OompaLoompaDetailError(val errorMessage: String) : UiEvent

}