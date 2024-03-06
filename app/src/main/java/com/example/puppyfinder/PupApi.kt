package com.example.puppyfinder

import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedImage
import com.example.puppyfinder.model.BreedInfo
import com.example.puppyfinder.model.ImagesItem
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PupApi {
    @GET("v1/breeds")
    suspend fun getBreeds(@Query("api_key") apiKey: String = "live_LYYFOYLv9e9YKnnIrhm186pgGNpFo9SQhEVyDfR1BKClyjWPggToWerirHjwZm0a") : List<Breed>

    @GET("v1/breeds/search")
    suspend fun getSearchedBreed(@Query("q") query: String): List<Breed>

    @GET("v1/breeds/{id}")
    suspend fun getBreedById(@Path("id") breedId: Int) : BreedInfo

    @GET("v1/images/{id}")
    suspend fun getImage(@Path("id") id: String) : BreedImage

    @GET("v1/images/search?limit=20")
    suspend fun getImages() : List<ImagesItem>
}