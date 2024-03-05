package com.example.puppyfinder.app

import AppTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.puppyfinder.app.ui.BreedFinderApp
import com.example.puppyfinder.viewmodel.PupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val viewModel: PupViewModel by viewModels()
        setContent {
            AppTheme {
                BreedFinderApp(viewModel = viewModel, navController = rememberNavController())
            }
        }
    }
}
