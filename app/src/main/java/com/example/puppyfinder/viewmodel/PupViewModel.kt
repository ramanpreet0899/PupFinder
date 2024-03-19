package com.example.puppyfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puppyfinder.BreedRepository
import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedImage
import com.example.puppyfinder.model.BreedInfo
import com.example.puppyfinder.model.ImagesItem
import com.example.puppyfinder.model.PostImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class PupViewModel @Inject constructor(private val breedRepository: BreedRepository) : ViewModel() {
    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>> = _breeds

    private val _breedInfo = MutableLiveData<BreedInfo>()
    val breedInfo: LiveData<BreedInfo> = _breedInfo

    private val _breedImage = MutableLiveData<BreedImage>()
    val breedImage : LiveData<BreedImage> = _breedImage

    private val _selectedBreedId = MutableLiveData<Int>()
    val selectedBreedId: LiveData<Int> = _selectedBreedId

    private val _dogImages = MutableLiveData<List<ImagesItem>>()
    val images : LiveData<List<ImagesItem>> = _dogImages

    private val _postImage = MutableLiveData<PostImage>()
    val postImage : LiveData<PostImage> = _postImage

    init {
        loadBreeds()
    }

    fun loadBreeds() {
        viewModelScope.launch {
            _breeds.value = breedRepository.getBreeds()
        }
    }

    fun searchBreed(query: String) {
        viewModelScope.launch {
            _breeds.value = breedRepository.searchBreed(query)
        }
    }

    fun loadBreedInformation(id: Int) {
        viewModelScope.launch {
            _breedInfo.value = breedRepository.getBreedInfo(id)
        }
    }

    fun loadImage(imageId: String) {
        viewModelScope.launch {
            _breedImage.value = breedRepository.getBreedImage(imageId)
        }
    }

    fun setSelectedBreedId(id: Int) {
        _selectedBreedId.value = id
    }

    fun loadDogImages() {
        viewModelScope.launch {
            _dogImages.value = breedRepository.getDogImages()
        }
    }

    fun uploadYourDogImage(body: MultipartBody) {
        viewModelScope.launch {
            _postImage.value = breedRepository.uploadDogImage(body)
        }
    }
}