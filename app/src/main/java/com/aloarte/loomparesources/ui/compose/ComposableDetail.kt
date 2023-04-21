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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
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

    val showDialog: MutableState<Boolean> = remember {
        mutableStateOf(false)
    }
    val textToDisplayInDialog: MutableState<String> = remember {
        mutableStateOf("")
    }
    if (showDialog.value) {
        LongTextDialog(textToDisplayInDialog.value) {
            showDialog.value = false
            textToDisplayInDialog.value = ""
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
                .align(Alignment.Center)
        ) {
            PairText(R.string.detail_id, employeeId.toString())
            PairText(R.string.detail_mail, employee.email)
            PairText(R.string.detail_age, employee.age.toString())
            PairText(R.string.detail_profession, employee.profession)
            PairText(R.string.detail_description, employee.description, true) {
                //Expand description info clicked
                employee.description?.let {
                    showDialog.value = true
                    textToDisplayInDialog.value = it
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
                    showDialog.value = true
                    textToDisplayInDialog.value = it
                }

            }

        }

    }
}

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
