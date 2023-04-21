package com.aloarte.loomparesources.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.ui.theme.LoompaResourcesTheme
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun Home(modifier: Modifier = Modifier) {

    val viewModel = hiltViewModel<MainViewModel>()

    Column(Modifier.padding(horizontal = 20.dp)) {
        Text(text = "hola")
        OompaLoompaList(viewModel)

    }


}

@Composable
fun OompaLoompaList(viewModel: MainViewModel, modifier: Modifier = Modifier) {

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
            modifier = Modifier
                .padding(8.dp),
            text = text
        )
        CircularProgressIndicator(color = Color.Black)
    }

}


@Composable
fun OompaLoompaListItem(item: OompaLoompaBo) {

    Card() {
        Row(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
        ) {


            GlideImage(
                previewPlaceholder = R.mipmap.portait_placeholder,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .width(80.dp)
                    .height(60.dp),
                imageModel = item.image,
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )

            Text(
                text = item.id.toString(),
            )
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                text = item.firstName,
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LoompaResourcesTheme {
        Home()
    }
}