package com.aloarte.loomparesources.ui.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.ui.viewmodel.MainViewModel

@Composable
fun Home() {
    val viewModel = hiltViewModel<MainViewModel>()

    Column(Modifier.padding(horizontal = 20.dp)) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 10.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.wonka_logo),
                contentDescription = "Cancel icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(130.dp)
            )
        }
        OompaLoompaList(viewModel)
    }
}


