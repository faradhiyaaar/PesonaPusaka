package com.capstone.pesonapusaka.ui.profile

import androidx.lifecycle.ViewModel
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.data.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
): ViewModel() {
    fun getFavorite() = favoriteRepository.getFavorite()
    suspend fun deleteFavorite(favorite: Favorite) = favoriteRepository.delete(favorite)
}