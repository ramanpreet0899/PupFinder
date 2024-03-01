package com.example.puppyfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puppyfinder.BreedRepository
import com.example.puppyfinder.model.Breed
import com.example.puppyfinder.model.BreedImage
import com.example.puppyfinder.model.BreedInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PupViewModel @Inject constructor(private val breedRepository: BreedRepository) : ViewModel() {
    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>> = _breeds

    private val _breedInfo = MutableLiveData<BreedInfo>()
    val breedInfo: LiveData<BreedInfo> = _breedInfo

    private val _breedImage = MutableLiveData<BreedImage>()
    val breedImage : LiveData<BreedImage> = _breedImage

    init {
        loadBreeds()
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            _breeds.value = breedRepository.getBreeds()
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
}