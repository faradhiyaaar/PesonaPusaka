package com.capstone.pesonapusaka.ui.wisatakuliner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.data.repository.FavoriteRepository
import com.capstone.pesonapusaka.data.repository.Repository
import com.capstone.pesonapusaka.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WisataKulinerViewModel @Inject constructor(
    private val repository: Repository,
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    private val _wisataKuliner = MutableStateFlow<Result<List<WisataKuliner>>>(Result.Void())
    val wisataKuliner = _wisataKuliner.asStateFlow()

    init {
        getUmkmRecommendation()
    }

    fun addToFavorite(favorite: Favorite) {
        viewModelScope.launch {
            favoriteRepository.postFavorite(favorite)
        }
    }

    private fun getUmkmRecommendation() {
        viewModelScope.launch {
            repository.getWisataKuliner().collect {
                _wisataKuliner.value = it
            }
        }
    }
}