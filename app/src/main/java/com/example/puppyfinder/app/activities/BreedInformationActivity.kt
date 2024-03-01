package com.example.puppyfinder.app.activities

import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.puppyfinder.R
import com.example.puppyfinder.viewmodel.PupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreedInformationActivity : ComponentActivity() {
    private val viewModel: PupViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                remember { mutableStateOf("") }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    viewModel.loadBreedInformation(intent.getIntExtra("dogId", 0))
                    ShowScreen(viewModel)
                }
            }
        }
    }

    @Composable
    private fun ShowScreen(viewModel: PupViewModel) {
        val data by remember(viewModel) { viewModel.breedInfo }.observeAsState()
        Text(
            text = data?.name.orEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(
                    R.font.playpen_sans_regular,
                    FontWeight.Normal
                )
            ),
            fontSize = 20.sp
        )
        DogDescriptionRow(R.drawable.ic_weight, data?.weight?.metric.orEmpty() )
        DogDescriptionRow(R.drawable.ic_height, data?.height?.metric.orEmpty())
        DogDescriptionRow(R.drawable.ic_weight, data?.lifeSpan.orEmpty())
        DogDescriptionRow(R.drawable.ic_weight, data?.breedGroup.orEmpty())
        DogDescriptionRow(R.drawable.ic_weight, data?.bredFor.orEmpty())
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Gallery",
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(
                    R.font.playpen_sans_regular,
                    FontWeight.Normal
                )
            ),
            fontSize = 20.sp
        )
       // CreateImageGallery()
    }

//    @Composable
//    fun CreateImageGallery() {
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(3),
//            content = {},
//            modifier = Modifier.padding(5.dp)
//        ) {
//            //
//        }
//    }

    @Composable
    fun DogDescriptionRow(icon: Int, description: String) {
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .shadow(1.dp, shape = RectangleShape)
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "imageDescription",
                    modifier = Modifier
                        .size(38.dp, 38.dp)
                        .padding(5.dp)
                )
                Text(
                    text = description,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily(
                        Font(
                            R.font.playpen_sans_regular,
                            FontWeight.Normal
                        )
                    ),
                    fontSize = 14.sp
                )
            }
        }
    }

//    @Preview
//    @Composable
//    fun SimpleComposablePreview() {
//        AppTheme {
//            remember { mutableStateOf("") }
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(16.dp)
//            ) {
//                ShowScreen()
//            }
//        }
//    }

}