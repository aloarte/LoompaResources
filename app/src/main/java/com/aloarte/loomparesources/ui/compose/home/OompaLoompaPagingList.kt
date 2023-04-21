package com.aloarte.loomparesources.ui.compose.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel


@Composable
fun OompaLoompaList(viewModel: MainViewModel) {
    val oompaLoompas = viewModel.getOompaLoompas().collectAsLazyPagingItems()
    LazyColumn {
        items(
            items = oompaLoompas
        ) { oompaLoompa ->
            oompaLoompa?.let {
                OompaLoompaListItem(oompaLoompa)
            }
            Divider(thickness = 5.dp, color = Color.Unspecified)
        }

        when (val state = oompaLoompas.loadState.refresh) { //FIRST LOAD
            is LoadState.Error -> {
                item { PagingComponentError(state.error.message ?: "") }
            }

            is LoadState.Loading -> { // Loading UI
                item { PagingComponentStatus("Initial loading") }
            }

            else -> {}
        }

        when (val state = oompaLoompas.loadState.append) { // Pagination
            is LoadState.Error -> {
                item { PagingComponentError(state.error.message ?: "") }
            }

            is LoadState.Loading -> { // Pagination Loading UI
                item { PagingComponentStatus("Loading") }
            }

            else -> {}
        }
    }
}

@Composable
fun PagingComponentStatus(text: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp),
            text = text
        )
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun PagingComponentError(text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp),
            text = text
        )
        CircularProgressIndicator(color = Color.Black)
    }
}