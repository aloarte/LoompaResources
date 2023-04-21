package com.aloarte.loomparesources.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.ui.UiEvent
import com.aloarte.loomparesources.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WillyWonkaRepository) :
    ViewModel() {

    private val _state = MutableStateFlow(UiState())

    fun getOompaLoompas(): Flow<PagingData<OompaLoompaBo>> =
        repository.getOompaLoompas().cachedIn(viewModelScope)

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.LoadHome -> {
                _state.update { it.copy(detailRequested = false, homeRequested = true) }
            }
            UiEvent.LoadDetail -> {
                _state.update { it.copy(detailRequested = true, homeRequested = false) }
            }

            is UiEvent.OompaLoompaDetail -> TODO()
            is UiEvent.OompaLoompaDetailError -> TODO()
            is UiEvent.OompaLoompasList -> TODO()
            is UiEvent.OompaLoompasListError -> TODO()
        }
    }


}