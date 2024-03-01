package com.example.puppyfinder

import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PupApi {
    @GET("v1/breeds")
    suspend fun getBreeds(@Query("api_key") apiKey: String = "live_LYYFOYLv9e9YKnnIrhm186pgGNpFo9SQhEVyDfR1BKClyjWPggToWerirHjwZm0a") : List<Breed>

    @GET("v1/breeds/{id}")
    suspend fun getBreedById(@Path("id") breedId: Int) : BreedInfo
}