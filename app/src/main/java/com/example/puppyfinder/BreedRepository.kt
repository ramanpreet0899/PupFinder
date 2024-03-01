package com.example.puppyfinder

import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedImage
import com.example.puppyfinder.model.BreedInfo

class BreedRepository(val pupApi: PupApi) {
    suspend fun getBreeds(): List<Breed> {
       return pupApi.getBreeds()
    }

    suspend fun searchBreed(query: String): List<Breed> {
        return pupApi.getSearchedBreed(query)
    }

    suspend fun getBreedInfo(id: Int) : BreedInfo {
        return pupApi.getBreedById(id)
    }

    suspend fun getBreedImage(imageId: String) : BreedImage {
        return pupApi.getImage(imageId)
    }
}