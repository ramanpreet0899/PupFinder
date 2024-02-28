package com.example.puppyfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.puppyfinder.BreedRepository
import com.example.puppyfinder.model.BreedsItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PupViewModel @Inject constructor(private val breedRepository: BreedRepository) : ViewModel() {
    private val _breeds = MutableLiveData<List<BreedsItem>>()
    val breeds : LiveData<List<BreedsItem>> = _breeds

    init {
        loadBreeds()
    }

    private fun loadBreeds() {
        viewModelScope.launch { _breeds.value = breedRepository.getBreeds() }
    }
}