package com.aloarte.loomparesources.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aloarte.loomparesources.domain.model.OompaLoompaBo

@Composable
fun Detail(employee: OompaLoompaBo) {
    Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()){
        Text("Loaded ${employee.firstName}")

    }

}