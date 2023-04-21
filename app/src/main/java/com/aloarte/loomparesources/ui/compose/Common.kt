package com.aloarte.loomparesources.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.domain.model.FavoriteBo

@Composable
fun LongTextDialog(description: String?, onDismiss: () -> Unit) {
    description?.let {
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(0.6f)
                        .fillMaxWidth(1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Box {
                        Row(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(horizontal = 10.dp, vertical = 10.dp)
                        ) {
                            Image(
                                painter = painterResource(R.drawable.ic_cancel),
                                contentDescription = "Cancel icon",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(20.dp)
                                    .height(20.dp)
                                    .clickable(onClick = onDismiss)
                            )
                        }

                        LazyColumn(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(horizontal = 25.dp, vertical = 25.dp)
                                .fillMaxWidth(1f)
                                .fillMaxHeight(1f),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            item { Text(text = it) }
                        }
                    }


                }
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}

@Composable
fun FavoritesDialog(favorites: FavoriteBo, firstName: String, onDismiss: () -> Unit) {
    Dialog(
        onDismissRequest = onDismiss,
        content = {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.6f)
                    .fillMaxWidth(1f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box {
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(horizontal = 10.dp, vertical = 10.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_cancel),
                            contentDescription = "Cancel icon",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                                .clickable(onClick = onDismiss)
                        )
                    }

                    Column(
                        Modifier
                            .align(Alignment.TopCenter)
                            .padding(horizontal = 25.dp, vertical = 25.dp)
                    ) {
                        Text(
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            text = "Favorite things from $firstName"
                        )
                        PairText(
                            label = R.string.detail_favorite_food,
                            employeeData = favorites.food
                        )
                        PairText(
                            label = R.string.detail_favorite_color,
                            employeeData = favorites.color
                        )
                        Text(
                            fontSize = 20.sp,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.ExtraBold,
                            text = "Song:"
                        )
                        Divider(thickness = 5.dp, color = Color.Unspecified)
                        LazyColumn(
                            modifier = Modifier

                                .fillMaxWidth(1f)
                                .fillMaxHeight(1f),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            item { Text(text = favorites.song) }
                        }
                    }
                }
            }
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    )
}

@Composable
fun PairText(
    label: Int,
    employeeData: String?,
    longText: Boolean = false,
    infoIconClicked: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                text = stringResource(id = label)
            )
            Text(
                fontSize = 20.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 0.dp),
                text = employeeData ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        }
        if (longText) {
            Image(
                painter = painterResource(R.drawable.ic_info),
                contentDescription = "Info icon",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .width(15.dp)
                    .height(15.dp)
                    .clickable(onClick = infoIconClicked)
            )
        }
    }
}

