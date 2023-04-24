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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.ui.state.UiEvent
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel

@Composable
fun OompaLoompaList(viewModel: MainViewModel) {
    val oompaLoompas = viewModel.getOompaLoompas().collectAsLazyPagingItems()
    LazyColumn {

        items(
            items = oompaLoompas
        ) { oompaLoompa ->
            oompaLoompa?.let {
                OompaLoompaListItem(oompaLoompa){
                    //If the card item gets clicked trigger the LoadDetail ui event
                    viewModel.onEvent(UiEvent.LoadDetail(oompaLoompaId = oompaLoompa.id))
                }
            }
            Divider(thickness = 5.dp, color = Color.Unspecified)
        }

        //View for the first load state
        when (val state = oompaLoompas.loadState.refresh) {
            is LoadState.Error -> {
                item { PagingComponentError(state.error.message ?: "") }
            }

            is LoadState.Loading -> {
                item { PagingComponentStatus(stringResource(id = R.string.list_init_load)) }
            }

            else -> {}
        }
        //View for the pagination load state
        when (val state = oompaLoompas.loadState.append) {
            is LoadState.Error -> {
                item { PagingComponentError(state.error.message ?: "") }
            }

            is LoadState.Loading -> {
                item { PagingComponentStatus(stringResource(id = R.string.list_load)) }
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
    }
}