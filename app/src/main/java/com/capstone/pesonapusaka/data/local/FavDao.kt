package com.capstone.pesonapusaka.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.capstone.pesonapusaka.data.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(fav: Favorite)
    @Delete
    suspend fun delete(fav: Favorite)
    @Query("SELECT * FROM Favorite")
    fun getFavorites(): Flow<List<Favorite>>
}