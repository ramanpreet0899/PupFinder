package com.example.puppyfinder.model

data class BreedX(
    val bred_for: String,
    val breed_group: String,
    val country_code: String,
    val height: HeightX,
    val id: Int,
    val life_span: String,
    val name: String,
    val origin: String,
    val reference_image_id: String,
    val temperament: String,
    val weight: WeightX
)