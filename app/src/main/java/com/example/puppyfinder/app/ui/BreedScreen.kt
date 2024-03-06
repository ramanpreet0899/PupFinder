package com.example.puppyfinder.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.puppyfinder.R
import com.example.puppyfinder.viewmodel.PupViewModel

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
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
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
                navigateUp = { navController.navigateUp() }
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

