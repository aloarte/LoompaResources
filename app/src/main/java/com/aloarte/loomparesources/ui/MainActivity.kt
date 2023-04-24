package com.aloarte.loomparesources.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.aloarte.loomparesources.ui.compose.detail.Detail
import com.aloarte.loomparesources.ui.compose.detail.DetailErrorLoading
import com.aloarte.loomparesources.ui.compose.detail.DetailLoadingLottie
import com.aloarte.loomparesources.ui.compose.home.Home
import com.aloarte.loomparesources.ui.state.ScreenStatus
import com.aloarte.loomparesources.ui.state.UiEvent
import com.aloarte.loomparesources.ui.theme.LoompaResourcesTheme
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = viewModel.state.collectAsState().value
            BackHandler {
                //The back is allowed from the detail or from the error screen.
                if (state.oompaLoompaDetail != null || state.errorMessage != null) {
                    viewModel.onEvent(UiEvent.LoadHome)
                }
            }
            LoompaResourcesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when {
                        state.homeRequested == ScreenStatus.Requested -> {
                            Home(
                                viewModel.getOompaLoompas().collectAsLazyPagingItems(),
                                viewModel::onEvent
                            )
                        }

                        state.detailRequested == ScreenStatus.Success -> {
                            state.oompaLoompaDetail?.let {
                                Detail(
                                    employee = state.oompaLoompaDetail,
                                    employeeId = state.oompaLoompaDetailId
                                ) {
                                    viewModel.onEvent(UiEvent.LoadHome)
                                }
                            }
                        }

                        state.detailRequested == ScreenStatus.Requested -> {
                            DetailLoadingLottie()
                        }

                        state.detailRequested == ScreenStatus.Error -> {
                            DetailErrorLoading(state.errorMessage) {
                                viewModel.onEvent(UiEvent.LoadHome)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Send the first event
        viewModel.onEvent(UiEvent.LoadHome)

    }
}
