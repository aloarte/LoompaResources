package com.aloarte.loomparesources.ui.compose.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel

@Composable
fun Home() {
    val viewModel = hiltViewModel<MainViewModel>()

    Column(Modifier.padding(horizontal = 20.dp)) {
        OompaLoompaList(viewModel)

    }
}


