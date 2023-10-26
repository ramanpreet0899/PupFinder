package com.example.puppyfinder.model

import androidx.compose.ui.graphics.painter.Painter

data class Breed(
    val name: String,
    val id: Int,
    val image: Painter
)
