package com.example.puppyfinder.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.puppyfinder.viewmodel.PupViewModel

@Composable
fun DogImages(viewModel: PupViewModel, modifier: Modifier) {
    viewModel.loadDogImages()
    val images  by remember(viewModel) { viewModel.images }.observeAsState(emptyList())
    Column(modifier = modifier) {
        LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(4.dp)) {
            items(images.size) {index ->
                AsyncImage(
                    model = images[index].url,
                    contentDescription = "image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp, 150.dp)
                )
            }
        }
    }

}