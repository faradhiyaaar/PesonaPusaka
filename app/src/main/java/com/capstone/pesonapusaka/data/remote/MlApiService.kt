package com.capstone.pesonapusaka.data.remote

import com.capstone.pesonapusaka.data.model.CandiResponse
import com.capstone.pesonapusaka.data.model.WisataKulinerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MlApiService {

    @GET("recommendation-candi-v2")
    suspend fun getCandiRecommendation(): Response<CandiResponse>

    @GET("recommendation-wisata-kuliner")
    suspend fun getWisataKulinerRecommendation(
        @Query("latitude") lat: Double,
        @Query("longitude") lng: Double
    ): Response<WisataKulinerResponse>
}