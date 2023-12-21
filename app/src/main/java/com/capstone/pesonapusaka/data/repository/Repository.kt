package com.capstone.pesonapusaka.data.repository

import android.util.Log
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.StoryModel
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.data.remote.ApiService
import com.capstone.pesonapusaka.data.remote.MlApiService
import com.capstone.pesonapusaka.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class Repository(
    private val apiService: ApiService,
    private val mlApiService: MlApiService
) {
    private val _candi = MutableStateFlow<Result<List<CandiModel>>>(Result.Void())
    private val _candiRecommendation = MutableStateFlow<Result<List<CandiModel>>>(Result.Void())
    private val _detailCandi = MutableStateFlow<Result<CandiModel>>(Result.Void())

    private val _story = MutableStateFlow<Result<List<StoryModel>>>(Result.Void())

    private val _postStory = MutableStateFlow<Result<String>>(Result.Void())

    private val _wisataKuliner = MutableStateFlow<Result<List<WisataKuliner>>>(Result.Void())
    private val _detailWisataKuliner = MutableStateFlow<Result<WisataKuliner>>(Result.Void())
    private val _wisataKulinerRecommendation =
        MutableStateFlow<Result<List<WisataKuliner>>>(Result.Void())

    suspend fun getAllCandi(): Flow<Result<List<CandiModel>>> {
        try {
            val client = apiService.getCandi()
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _candi.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _candi.value = Result.Error(e.message())
        }

        return _candi.asStateFlow()
    }

    suspend fun getCandiRecommendation(): Flow<Result<List<CandiModel>>> {
        try {
            val client = mlApiService.getCandiRecommendation()
            if (client.isSuccessful) {
                val candi = client.body()?.data
                _candiRecommendation.value = Result.Success(candi!!)
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _candiRecommendation.value = Result.Error(e.message())
        }

        return _candiRecommendation.asStateFlow()
    }

    suspend fun getCandiById(id: Int): Flow<Result<CandiModel>> {
        try {
            val client = apiService.getDetailCandi(id)
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _detailCandi.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _detailCandi.value = Result.Error(e.message())
        }

        return _detailCandi.asStateFlow()
    }

    suspend fun getAllStories(): Flow<Result<List<StoryModel>>> {
        _story.value = Result.Loading()
        try {
            val client = apiService.getAllStories()
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _story.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _story.value = Result.Error(e.message())
        }

        return _story.asStateFlow()
    }

    suspend fun getWisataKuliner(): Flow<Result<List<WisataKuliner>>> {
        try {
            val client = apiService.getWisataKuliner()
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _wisataKuliner.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _wisataKuliner.value = Result.Error(e.message())
        }

        return _wisataKuliner.asStateFlow()
    }

    suspend fun getWisataKulinerById(id: Int): Flow<Result<WisataKuliner>> {
        try {
            val client = apiService.getWisataKulinerById(id)
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _detailWisataKuliner.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _detailWisataKuliner.value = Result.Error(e.message())
        }

        return _detailWisataKuliner.asStateFlow()
    }

    suspend fun getWisataKulinerRecommendation(
        lat: Double,
        lng: Double
    ): Flow<Result<List<WisataKuliner>>> {
        try {
            val client = mlApiService.getWisataKulinerRecommendation(lat, lng)
            if (client.isSuccessful) {
                val candi = client.body()?.data
                candi?.let {
                    _wisataKulinerRecommendation.value = Result.Success(it)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _wisataKulinerRecommendation.value = Result.Error(e.message())
        }

        return _wisataKulinerRecommendation.asStateFlow()
    }

    suspend fun postStory(
        image: File,
        description: String,
        name: String,
        userId: String
    ): Flow<Result<String>> {

        val requestImageFile = image.asRequestBody("image/*".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "image",
            image.name,
            requestImageFile
        )
        val id = userId.toRequestBody("text/plain".toMediaType())
        val title = name.toRequestBody("text/plain".toMediaType())
        val descriptionCast = description.toRequestBody("text/plain".toMediaType())

        try {
            val client =
                apiService.postStory(
                    id,
                    title,
                    imageMultipart,
                    descriptionCast
                )
            if (client.isSuccessful) {
                _postStory.value = Result.Success("Berhasil")
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _postStory.value = Result.Error(e.message())
        }

        return _postStory.asStateFlow()
    }
}