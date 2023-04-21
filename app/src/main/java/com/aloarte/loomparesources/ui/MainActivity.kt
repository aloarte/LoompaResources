package com.aloarte.loomparesources.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.aloarte.loomparesources.ui.compose.Detail
import com.aloarte.loomparesources.ui.compose.home.Home
import com.aloarte.loomparesources.ui.state.ScreenStatus
import com.aloarte.loomparesources.ui.theme.LoompaResourcesTheme
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            LoompaResourcesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = viewModel.state.collectAsState().value
                    when{
                        state.homeRequested == ScreenStatus.Requested ->{
                            Home()
                        }
                        state.detailRequested == ScreenStatus.Success  ->{
                            state.oompaLoompaDetail?.let{
                                Detail(employee = state.oompaLoompaDetail)
                            }
                        }
                        state.detailRequested == ScreenStatus.Requested  ->{
                            Text("LOADING")
                        }
                        state.detailRequested == ScreenStatus.Error  ->{
                            Text("ERRORRRRR ${state.errorMessage}")
                        }
                    }

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onEvent(UiEvent.LoadHome)

    }
}
