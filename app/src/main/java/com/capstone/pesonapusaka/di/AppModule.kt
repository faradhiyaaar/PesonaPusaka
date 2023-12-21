package com.capstone.pesonapusaka.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.capstone.pesonapusaka.data.local.FavDao
import com.capstone.pesonapusaka.data.local.FavDatabase
import com.capstone.pesonapusaka.data.remote.ApiService
import com.capstone.pesonapusaka.data.remote.MlApiService
import com.capstone.pesonapusaka.data.repository.AuthRepository
import com.capstone.pesonapusaka.data.repository.FavoriteRepository
import com.capstone.pesonapusaka.data.repository.Repository
import com.capstone.pesonapusaka.utils.Dimens.BASE_URL
import com.capstone.pesonapusaka.utils.Dimens.FAVOURITE_DB
import com.capstone.pesonapusaka.utils.Dimens.ML_BASE_URL
import com.capstone.pesonapusaka.utils.getInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideMlApiService(): MlApiService {
        return Retrofit.Builder()
            .baseUrl(ML_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getInterceptor())
            .build()
            .create(MlApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        apiService: ApiService,
        @ApplicationContext context: Context
    ): AuthRepository = AuthRepository(apiService, context)

    @Provides
    @Singleton
    fun provideRepository(
        apiService: ApiService,
        mlApiService: MlApiService
    ): Repository = Repository(apiService, mlApiService)

    @Provides
    @Singleton
    fun provideDatabase(
        application: Application
    ): FavDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = FavDatabase::class.java,
            name = FAVOURITE_DB
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(
        favDatabase: FavDatabase
    ): FavDao = favDatabase.favDao

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favDao: FavDao
    ): FavoriteRepository = FavoriteRepository(favDao)
}