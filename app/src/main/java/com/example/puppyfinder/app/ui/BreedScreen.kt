package com.example.puppyfinder.app.ui

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.puppyfinder.R
import com.example.puppyfinder.viewmodel.PupViewModel
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.ByteArrayOutputStream
import java.io.File


/**
 * enum values that represent the screens in the app
 */
enum class BreedScreen(val title: Int) {
    Start(title = R.string.breeds_list),
    Information(title = R.string.breed_information),
    Images(title = R.string.images)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedFinderAppBar(
    currentScreen: BreedScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    showMenu: Boolean,
    viewModel: PupViewModel
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        actions = {
            if (showMenu) {
                val context = LocalContext.current
                val getContent : ActivityResultLauncher<String>
                var imageUri by remember { mutableStateOf<Uri?>(null) }
                getContent = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                    imageUri = uri
                }
                IconButton(onClick = {
                    getContent.launch("image/*")
                    imageUri?.let { it ->
                        val inputStream = context.contentResolver.openInputStream(it)
                        val bitmap = inputStream?.use { stream ->
                            BitmapFactory.decodeStream(stream)
                        }
                        bitmap?.let {b ->
                            // Convert the image to bytes
                            val byteStream = ByteArrayOutputStream()
                            b.compress(android.graphics.Bitmap.CompressFormat.JPEG, 100, byteStream)
                            val byteArray = byteStream.toByteArray()
                            uploadImage(byteArray, viewModel = viewModel)
                        }
                    }
                }) {
                    Icon(Icons.Default.AddCircle, contentDescription = "shareMenu")
                }
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

fun uploadImage(imageUri: ByteArray, viewModel: PupViewModel) {
    val requestBody = MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("file", "great.jpg", RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageUri))
        .addFormDataPart("sub_id", "")
        .build()

    viewModel.uploadYourDogImage(body = requestBody)
}

@Composable
fun BreedFinderApp(
    viewModel: PupViewModel,
    navController: NavHostController
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = BreedScreen.valueOf(
        backStackEntry?.destination?.route ?: BreedScreen.Start.name
    )

    Scaffold(
        topBar = {
            BreedFinderAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                showMenu = navController.currentDestination?.route == BreedScreen.Images.name,
                viewModel = viewModel
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = BreedScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = BreedScreen.Start.name) {
                ShowBreedScreen(
                    viewModel = viewModel,
                    modifier = Modifier
                        .fillMaxSize(),
                    onRowItemClick = { breedId ->
                        viewModel.setSelectedBreedId(breedId)
                        navController.navigate(BreedScreen.Information.name)
                    }
                )
            }
            composable(route = BreedScreen.Information.name) {
                BreedInformationScreen(
                    viewModel = viewModel,
                    modifier = Modifier.fillMaxSize(),
                    onExploreButtonClicked = {
                        navController.navigate(BreedScreen.Images.name)
                    })
            }
            composable(route = BreedScreen.Images.name) {
                DogImages(viewModel = viewModel, modifier = Modifier.fillMaxSize())
            }
        }

    }
}

