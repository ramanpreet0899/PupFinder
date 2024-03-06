package com.example.puppyfinder.app.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puppyfinder.R
import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.viewmodel.PupViewModel

@Composable
fun ShowBreedScreen(viewModel: PupViewModel, modifier: Modifier, onRowItemClick: (Int) -> Unit) {
    Column(
        modifier
    ) {
        val searchQuery = remember { mutableStateOf("") }
        SearchBar(searchQuery.value, onQueryChange = {
            searchQuery.value = it
            if (searchQuery.value.isEmpty()) {
                viewModel.loadBreeds()
            } else {
                viewModel.searchBreed(searchQuery.value)
            }
        }, {})
        val data by remember(viewModel) { viewModel.breeds }.observeAsState(emptyList())
        ShowBreeds(breeds = data, onBreedClick = onRowItemClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query: String, onQueryChange: (String) -> Unit, onSearchButtonClick: () -> Unit
) {
    TextField(
        value = query,
        onValueChange = { newQuery -> onQueryChange(newQuery) },
        label = {
            Text(
                stringResource(R.string.find_your_pup),
                fontFamily = FontFamily(Font(R.font.playpen_sans_bold, FontWeight.Bold))
            )
        },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(1.dp),
        textStyle = TextStyle(
            fontFamily = FontFamily(Font(R.font.playpen_sans_regular, FontWeight.Normal)),
            fontSize = 14.sp
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearchButtonClick() }
        )

    )
}

@Composable
fun ShowBreeds(breeds: List<Breed>, onBreedClick: (Int) -> Unit) {
    LocalContext.current
    LazyColumn {
        items(breeds.size) { index ->
            Row(
                modifier = Modifier
                    .clickable(onClick = {
                        onBreedClick(breeds[index].id)
                    })
                    .shadow(shape = RectangleShape, elevation = 0.5.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = breeds[index].name,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(
                        Font(
                            R.font.playpen_sans_regular,
                            FontWeight.Normal
                        )
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

