package com.capstone.pesonapusaka.data.repository

import com.capstone.pesonapusaka.data.local.FavDao
import com.capstone.pesonapusaka.data.model.Favorite
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(
    private val favDao: FavDao
) {
    fun getFavorite(): Flow<List<Favorite>> {
        return favDao.getFavorites()
    }
    suspend fun postFavorite(favorite: Favorite) {
        favDao.upsert(favorite)
    }
    suspend fun delete(favorite: Favorite) {
        favDao.delete(favorite)
    }
}