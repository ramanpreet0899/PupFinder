package com.example.puppyfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import com.example.puppyfinder.model.BreedsItem
import com.example.puppyfinder.viewmodel.PupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: PupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val searchQuery = remember { mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                SearchBar(searchQuery.value, onQueryChange = { searchQuery.value = it }, {})
                showScreen(viewModel.breeds)
            }
        }
    }

    @Composable
    private fun showScreen(breeds: LiveData<List<BreedsItem>>) {
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
                    fontFamily = FontFamily(Font(R.font.playpen_sans_thin, FontWeight.Light))
                )
            },
            singleLine = true,
            modifier = Modifier
                .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
                .fillMaxWidth()
                .shadow(1.dp),
            textStyle = TextStyle(
                Color.Blue,
                fontFamily = FontFamily(Font(R.font.playpen_sans_semi_bold, FontWeight.SemiBold)),
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
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(top = 10.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .shadow(0.5.dp)
                            .width(50.dp)
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.dog),
                            contentDescription = "breed image",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
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