package com.aloarte.loomparesources.ui.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aloarte.loomparesources.Constants.FEMALE
import com.aloarte.loomparesources.Constants.MALE
import com.aloarte.loomparesources.Constants.PROF_BREWER
import com.aloarte.loomparesources.Constants.PROF_DEVELOPER
import com.aloarte.loomparesources.Constants.PROF_GEMCUTTER
import com.aloarte.loomparesources.Constants.PROF_MEDIC
import com.aloarte.loomparesources.Constants.PROF_METAL_WORKER
import com.aloarte.loomparesources.R
import com.aloarte.loomparesources.domain.model.OompaLoompaBo
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage


@Composable
fun OompaLoompaListItem(employee: OompaLoompaBo) {
    Card {
        Row(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp)
        ) {
            ListCardImageAndId(employee)
            ListCardEmployeeInfo(employee)
        }
    }
}

@Composable
fun ListCardEmployeeInfo(employee: OompaLoompaBo) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 10.dp)
    ) {

        Row(modifier = Modifier.align(Alignment.TopEnd)) {
            Image(
                painterResource(getProfessionIcon(employee.profession)),
                contentDescription = employee.profession,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp)
            )
            Image(
                painterResource(getGenderIcon(employee.gender)),
                contentDescription = employee.gender,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(22.dp)
                    .height(22.dp)
            )
        }

        Column(
            Modifier
                .align(Alignment.CenterStart)
                .padding(horizontal = 10.dp)
        ) {
            Row {
                Text(
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = employee.firstName
                )
                Text(
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 5.dp, vertical = 0.dp),
                    text = employee.lastName
                )
            }

            Row {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    text = employee.email,
                )
            }
        }


    }

}


@Composable
fun ListCardImageAndId(item: OompaLoompaBo) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        GlideImage(
            previewPlaceholder = R.mipmap.portait_placeholder,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(50.dp)
                .height(50.dp)
                .clip(CircleShape),

            imageModel = item.image,
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        )

        Divider(thickness = 5.dp, color = Color.Unspecified)

        Text(
            style = MaterialTheme.typography.titleMedium,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            text = stringResource(id = R.string.employee_id),
        )
        Text(
            style = MaterialTheme.typography.titleMedium,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            text = item.id.toString()
        )
    }
}


fun getGenderIcon(gender: String) = when (gender) {
    FEMALE -> R.drawable.ic_female
    MALE -> R.drawable.ic_male
    else -> R.drawable.ic_agender
}


fun getProfessionIcon(profession: String) = when (profession) {
    PROF_DEVELOPER -> R.drawable.ic_developer
    PROF_METAL_WORKER -> R.drawable.ic_metalcrafter
    PROF_GEMCUTTER -> R.drawable.ic_gemcutter
    PROF_MEDIC -> R.drawable.ic_medic
    PROF_BREWER -> R.drawable.ic_brewer
    else -> R.drawable.ic_unknown_prof
}