package com.aloarte.loomparesources.ui

import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.domain.model.OompaLoompaContentBo

sealed interface UiEvent {
    object LoadHome : UiEvent
    object LoadDetail : UiEvent
    class OompaLoompasList(val contents: OompaLoompaContentBo) : UiEvent
    data class OompaLoompasListError(val errorMessage: String) : UiEvent
    data class OompaLoompaDetail(val oompaLoompaId: Int, val oompaLoompa: OompaLoompaBo?) : UiEvent
    data class OompaLoompaDetailError(val errorMessage: String) : UiEvent

}