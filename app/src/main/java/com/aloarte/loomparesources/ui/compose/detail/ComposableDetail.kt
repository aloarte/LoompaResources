package com.aloarte.loomparesources.ui.compose.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.aloarte.loomparesources.ui.compose.FavoritesDialog
import com.aloarte.loomparesources.ui.compose.LongTextDialog
import com.aloarte.loomparesources.ui.compose.PairText
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun Detail(employee: OompaLoompaBo, employeeId: Int, onExitClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            GlideImage(
                previewPlaceholder = R.mipmap.portait_placeholder,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .align(Alignment.TopCenter),
                imageModel = employee.image,
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .clickable(onClick = onExitClick),
            ) {
                Image(
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp),
                    painter = painterResource(R.drawable.ic_cancel),
                    contentDescription = "Info icon",
                    contentScale = ContentScale.Crop,

                    )
            }

        }


        EmployeeInfo(
            employee = employee,
            employeeId = employeeId,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )


    }

}

@Composable
fun EmployeeInfo(employee: OompaLoompaBo, employeeId: Int, modifier: Modifier) {

    val showLongTextDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val favoritesDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val longText: MutableState<String> = remember {
        mutableStateOf("")
    }
    if (showLongTextDialog.value) {
        LongTextDialog(longText.value) {
            //The dialog was canceled/quited
            showLongTextDialog.value = false
            longText.value = ""
        }
    }
    if (favoritesDialog.value) {
        FavoritesDialog(employee.favorite, employee.firstName) {
            //The dialog was canceled/quited
            favoritesDialog.value = false
        }
    }

    Box(
        modifier = modifier
            .padding(vertical = 40.dp, horizontal = 50.dp)
    ) {
        Row(Modifier.align(Alignment.TopCenter)) {
            Text(
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                text = employee.firstName
            )
            Text(
                fontSize = 25.sp,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
                text = employee.lastName
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PairText(R.string.detail_id, employeeId.toString())
            PairText(R.string.detail_mail, employee.email)
            PairText(R.string.detail_age, employee.age.toString())
            PairText(R.string.detail_profession, employee.profession)
            PairText(R.string.detail_description, employee.description, true) {
                //Expand description info clicked
                employee.description?.let {
                    showLongTextDialog.value = true
                    longText.value = it
                }

            }
            PairText(R.string.detail_country, employee.country)

            PairText(
                R.string.detail_gender, when (employee.gender) {
                    "F" -> "Female"
                    "M" -> "Male"
                    else -> "Non binary"
                }
            )
            PairText(R.string.detail_height, employee.height.toString())
            PairText(R.string.detail_quoter, employee.quote, true) {
                //Expand quote info clicked
                employee.quote?.let {
                    showLongTextDialog.value = true
                    longText.value = it
                }

            }

            Divider(thickness = 20.dp, color = Color.Unspecified)
            Button(onClick = { favoritesDialog.value = true }) {
                Text(
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
                    text = "Favorites"
                )
            }
        }
    }
}
