package com.aloarte.loomparesources.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aloarte.loomparesources.ui.theme.LoompaResourcesTheme


@Composable
fun Home(modifier: Modifier = Modifier) {

    Text(
        text = "Hello",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LoompaResourcesTheme {
        Home()
    }
}