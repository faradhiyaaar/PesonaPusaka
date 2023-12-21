package com.capstone.pesonapusaka.data.remote

import com.capstone.pesonapusaka.data.model.CandiResponse
import com.capstone.pesonapusaka.data.model.DetailCandiResponse
import com.capstone.pesonapusaka.data.model.PostResponse
import com.capstone.pesonapusaka.data.model.SignInResponse
import com.capstone.pesonapusaka.data.model.StoryDetailResponse
import com.capstone.pesonapusaka.data.model.StoryResponse
import com.capstone.pesonapusaka.data.model.WisataKulinerDetailResponse
import com.capstone.pesonapusaka.data.model.WisataKulinerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    // Auth
    @Multipart
    @POST("register")
    suspend fun register(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part avatar: MultipartBody.Part
    ): Response<SignInResponse>

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<SignInResponse>

    @FormUrlEncoded
    @POST("forgot-password")
    suspend fun forgotPassword(
        @Field("email") email: String
    ): Response<SignInResponse>

    // Candi
    @GET("candi")
    suspend fun getCandi(): Response<CandiResponse>

    @GET("candi/{id}")
    suspend fun getDetailCandi(
        @Path("id") id: Int
    ): Response<DetailCandiResponse>

    // Story
    @GET("stories")
    suspend fun getAllStories(): Response<StoryResponse>

    @GET("stories/{id}")
    suspend fun getDetailStory(
        @Path("id") id: Int
    ): Response<StoryDetailResponse>

    @GET("stories_by_id_user/{id}")
    suspend fun getStoryByUserId(
        @Path("id") id: Int
    ): Response<StoryResponse>

    @Multipart
    @POST("stories")
    suspend fun postStory(
        @Part("id_user") idUser: RequestBody,
        @Part("title") title: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("content") content: RequestBody
    ): Response<PostResponse>

    // User
    @GET("user/{id}")
    suspend fun getUserById(
        @Path("id") id: Int
    ): Response<SignInResponse>

    // Wisata Kuliner
    @GET("wisata-kuliner")
    suspend fun getWisataKuliner(): Response<WisataKulinerResponse>

    @GET("wisata-kuliner/{id}")
    suspend fun getWisataKulinerById(
        @Path("id") id: Int
    ): Response<WisataKulinerDetailResponse>
}