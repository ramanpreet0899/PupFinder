package com.example.puppyfinder

import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedInfo

class BreedRepository(val pupApi: PupApi) {
    suspend fun getBreeds(): List<Breed> {
       return pupApi.getBreeds()
    }

    suspend fun getBreedInfo(id: Int) : BreedInfo {
        return pupApi.getBreedById(id)
    }
}