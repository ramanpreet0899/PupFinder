package com.example.puppyfinder.model

import com.google.gson.annotations.SerializedName

data class BreedInfo(
    @SerializedName("bred_for")
    val bredFor: String,
    @SerializedName("breed_group")
    val breedGroup: String,
    @SerializedName("height")
    val height: Height,
    @SerializedName("id")
    val id: Int,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("weight")
    val weight: Weight,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("reference_image_id")
    val imageId: String,
    @SerializedName("origin")
    val origin: String
)