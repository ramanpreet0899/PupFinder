package com.example.puppyfinder

import com.example.puppyfinder.model.BreedsItem

class BreedRepository(val pupApi: PupApi) {
    suspend fun getBreeds(): List<BreedsItem> {
       return pupApi.getBreeds()
    }
}