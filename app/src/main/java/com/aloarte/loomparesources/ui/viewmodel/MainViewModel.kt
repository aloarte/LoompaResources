package com.aloarte.loomparesources.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.aloarte.loomparesources.domain.WillyWonkaRepository
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.ui.UiEvent
import com.aloarte.loomparesources.ui.UiState
import com.aloarte.loomparesources.ui.state.ScreenStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WillyWonkaRepository) :
    ViewModel() {


    private val _state = MutableStateFlow(UiState())
    val state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun getOompaLoompas(): Flow<PagingData<OompaLoompaBo>> =
        repository.getOompaLoompas().cachedIn(viewModelScope)

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.LoadHome -> {
                _state.update {
                    it.copy(
                        detailRequested = ScreenStatus.NotRequested,
                        homeRequested = ScreenStatus.Requested,
                        oompaLoompaDetailId = -1,
                        oompaLoompaDetail = null,
                        errorMessage = null
                    )
                }
            }

            is UiEvent.LoadDetail -> {
                _state.update {
                    it.copy(
                        detailRequested = ScreenStatus.Requested,
                        homeRequested = ScreenStatus.NotRequested,
                        oompaLoompaDetailId = event.oompaLoompaId,
                        oompaLoompaDetail = null,
                        errorMessage = null
                    )
                }
                viewModelScope.launch {
                    val detail = repository.getOompaLoompa(event.oompaLoompaId)
                    detail?.let {
                        onEvent(UiEvent.OompaLoompaDetailSuccess(oompaLoompa = detail))
                    } ?: run {
                        onEvent(UiEvent.OompaLoompaDetailError(errorMessage = "Error loading the ${event.oompaLoompaId} employee"))
                    }
                }
            }

            is UiEvent.OompaLoompaDetailSuccess -> {
                _state.update {
                    it.copy(
                        detailRequested = ScreenStatus.Success,
                        homeRequested = ScreenStatus.NotRequested,
                        oompaLoompaDetail = event.oompaLoompa,
                        errorMessage = null
                    )
                }
            }

            is UiEvent.OompaLoompaDetailError -> {
                _state.update {
                    it.copy(
                        detailRequested = ScreenStatus.Error,
                        homeRequested = ScreenStatus.NotRequested,
                        oompaLoompaDetail = null,
                        errorMessage = event.errorMessage
                    )
                }
            }
        }
    }


}