package com.example.puppyfinder

import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedImage
import com.example.puppyfinder.model.BreedInfo
import com.example.puppyfinder.model.ImagesItem
import com.example.puppyfinder.model.PostImage
import okhttp3.MultipartBody

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

    suspend fun getDogImages() : List<ImagesItem> {
        return pupApi.getImages()
    }

    suspend fun uploadDogImage(requestBody: MultipartBody): PostImage {
        return pupApi.uploadImage(requestBody)
    }
}