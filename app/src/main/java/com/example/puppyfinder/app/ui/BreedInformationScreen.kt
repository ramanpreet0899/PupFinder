package com.example.puppyfinder.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.puppyfinder.R
import com.example.puppyfinder.model.BreedInfo
import com.example.puppyfinder.viewmodel.PupViewModel

@Composable
fun BreedInformationScreen(
    viewModel: PupViewModel,
    modifier: Modifier,
    onExploreButtonClicked: () -> Unit = {}
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        viewModel.selectedBreedId.value?.let { viewModel.loadBreedInformation(it) }
        ShowScreen(viewModel)
        ExploreOtherDogs(onExploreButtonClicked)
    }
}

@Composable
private fun ShowScreen(viewModel: PupViewModel) {
    val data by remember(viewModel) { viewModel.breedInfo }.observeAsState()
    data?.let {
        ShowImage(viewModel = viewModel, data = it)
    }
    Text(
        text = data?.name.orEmpty(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        textAlign = TextAlign.Center,
        fontFamily = FontFamily(
            Font(
                R.font.playpen_sans_regular, FontWeight.Normal
            )
        ),
        fontSize = 20.sp
    )
    DogDescriptionRow(
        stringResource(R.string.weight),
        stringResource(R.string.in_metric, data?.weight?.metric.orEmpty())
    )
    DogDescriptionRow(
        stringResource(R.string.height),
        stringResource(R.string.in_metric, data?.height?.metric.orEmpty())
    )
    DogDescriptionRow(stringResource(R.string.life_span), data?.lifeSpan.orEmpty())
    DogDescriptionRow(stringResource(R.string.breed_group), data?.breedGroup.orEmpty())
    DogDescriptionRow(stringResource(R.string.bred_for), data?.bredFor.orEmpty())
    DogDescriptionRow(stringResource(R.string.temperament), data?.temperament.orEmpty())
    DogDescriptionRow(stringResource(R.string.origin), data?.origin.orEmpty())
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun ShowImage(viewModel: PupViewModel, data: BreedInfo?) {
    viewModel.loadImage(data?.imageId.orEmpty())
    val image by remember(viewModel) { viewModel.breedImage }.observeAsState()
    Box(
        Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = image?.url.orEmpty(),
            contentDescription = "image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ExploreOtherDogs(onExploreButtonClicked: () -> Unit) {
    Button(
        onClick = onExploreButtonClicked, modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(34.dp)
    ) {
        Text(text = stringResource(R.string.explore_other_breeds))
    }
}

@Composable
fun DogDescriptionRow(dimension: String, description: String) {
    Spacer(modifier = Modifier.height(5.dp))
    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = dimension,
                modifier = Modifier.wrapContentWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(
                    Font(
                        R.font.playpen_sans_bold, FontWeight.Bold
                    )
                ),
                fontSize = 16.sp
            )
            Text(
                text = description,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                fontFamily = FontFamily(
                    Font(
                        R.font.playpen_sans_regular, FontWeight.Normal
                    )
                ),
                fontSize = 14.sp
            )
        }
    }
}
