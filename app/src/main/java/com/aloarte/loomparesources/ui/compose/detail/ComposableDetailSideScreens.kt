package com.aloarte.loomparesources.ui.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.aloarte.loomparesources.R

@Composable
fun DetailLoadingLottie() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.loading_anim
        )
    )
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 100.dp, horizontal = 50.dp),
    ) {

        LottieAnimation(
            composition = composition, restartOnPlay = true, iterations = 10, modifier = Modifier
                .width(400.dp)
                .height(400.dp)
        )

        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp),
            text = stringResource(id = R.string.detail_loading_message)
        )

    }
}

@Composable
fun DetailErrorLoading(errorMessage: String?, onBackListener: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(vertical = 100.dp, horizontal = 50.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.mipmap.sad_robot),
            contentDescription = "Sad robot",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(400.dp)
                .height(400.dp)
        )

        Text(
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 40.dp),
            text = errorMessage ?: stringResource(id = R.string.detail_loading_error_message)
        )

        Button(onClick = onBackListener, modifier = Modifier.width(150.dp)) {
            Text(
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
                text = "Back"
            )
        }
    }
}