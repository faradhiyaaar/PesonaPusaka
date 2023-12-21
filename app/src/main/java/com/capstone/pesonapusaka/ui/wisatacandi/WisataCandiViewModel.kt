package com.capstone.pesonapusaka.ui.wisatacandi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.data.repository.FavoriteRepository
import com.capstone.pesonapusaka.data.repository.Repository
import com.capstone.pesonapusaka.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WisataCandiViewModel @Inject constructor(
    private val repository: Repository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val _candi = MutableStateFlow<Result<List<CandiModel>>>(Result.Void())
    val candi = _candi.asStateFlow()

    init {
        getCandiRecommendation()
    }

    fun addToFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.postFavorite(favorite)
        }
    }

    private fun getCandiRecommendation() {
        viewModelScope.launch {
            repository.getAllCandi().collect {
                _candi.value = it
            }
        }
    }
}