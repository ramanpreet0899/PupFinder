package com.example.puppyfinder.model

import com.google.gson.annotations.SerializedName

data class BreedsItem(
    @SerializedName("bred_for")
    val bredFor: String,
    @SerializedName("breed_group")
    val breedGroup: String,
    @SerializedName("country_code")
    val countryCode: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("height")
    val height: Height,
    @SerializedName("history")
    val history: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("life_span")
    val lifeSpan: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin")
    val origin: String,
    @SerializedName("reference_image_id")
    val referenceImageId: String,
    @SerializedName("temperament")
    val temperament: String,
    @SerializedName("weight")
    val weight: Weight
)