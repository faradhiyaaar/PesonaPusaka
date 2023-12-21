package com.capstone.pesonapusaka.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone.pesonapusaka.data.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavDatabase: RoomDatabase() {
    abstract val favDao: FavDao
}