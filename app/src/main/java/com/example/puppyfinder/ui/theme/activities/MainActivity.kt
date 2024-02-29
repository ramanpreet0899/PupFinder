package com.example.puppyfinder.ui.theme.activities

import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puppyfinder.R
import com.example.puppyfinder.model.BreedsItem
import com.example.puppyfinder.viewmodel.PupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PupViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val searchQuery = remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    SearchBar(searchQuery.value, onQueryChange = { searchQuery.value = it }, {})
                    showScreen()
                }
            }
        }
    }

    @Composable
    private fun showScreen() {
        val data by remember(viewModel) { viewModel.breeds }.observeAsState(emptyList())
        ShowBreeds(breeds = data)
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
                    "find your pup",
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
    fun ShowBreeds(breeds: List<BreedsItem>) {
        LazyColumn {
            items(breeds.size) { index ->
                Row(
                    modifier = Modifier
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
}