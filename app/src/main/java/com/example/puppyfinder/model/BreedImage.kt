package com.example.puppyfinder.model

data class BreedImage(
    val breeds: List<BreedX>,
    val height: Int,
    val id: String,
    val url: String,
    val width: Int
)