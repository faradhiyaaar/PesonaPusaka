package com.capstone.pesonapusaka.data.repository

import android.content.Context
import android.util.Log
import com.capstone.pesonapusaka.data.local.Preference
import com.capstone.pesonapusaka.data.model.SignInResponse
import com.capstone.pesonapusaka.data.model.UserModel
import com.capstone.pesonapusaka.data.remote.ApiService
import com.capstone.pesonapusaka.utils.Dimens.AVATAR
import com.capstone.pesonapusaka.utils.Dimens.EMAIL
import com.capstone.pesonapusaka.utils.Dimens.NAME
import com.capstone.pesonapusaka.utils.Dimens.USER_ID
import com.capstone.pesonapusaka.utils.Result
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File


class AuthRepository(
    private val apiService: ApiService,
    private val context: Context
) {
    private val _userState = MutableStateFlow<Result<String>>(Result.Void())
    private val _user = MutableStateFlow<Result<UserModel>>(Result.Void())
    private val _forgotPasswordState = MutableStateFlow<Result<String>>(Result.Void())

    suspend fun register(
        image: File,
        name: String,
        email: String,
        password: String
    ): Flow<Result<String>> {
        _userState.value = Result.Loading()
        val requestImageFile = image.asRequestBody("image/*".toMediaType())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "avatar",
            image.name,
            requestImageFile
        )
        val nameCast = name.toRequestBody("text/plain".toMediaType())
        val emailCast = email.toRequestBody("text/plain".toMediaType())
        val passwordCast = password.toRequestBody("text/plain".toMediaType())

        try {
            val client = apiService.register(
                nameCast,
                emailCast,
                passwordCast,
                imageMultipart
            )
            if (client.isSuccessful) {
                client.body()?.let {
                    _userState.value = Result.Success(it.message!!)
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _userState.value = Result.Error(e.message())
        }

        return _userState.asStateFlow()
    }

    suspend fun signIn(email: String, password: String): Flow<Result<UserModel>> {
        _user.value = Result.Loading()
        try {
            val client = apiService.login(email, password)
            if (client.isSuccessful) {
                client.body()?.message?.let { it ->
                    if (it != "Username or Password is invalid") {
                        val user = client.body()?.data
                        user?.let {
                            Preference.saveToken(
                                it.name ?: "",
                                email,
                                it.avatar ?: "",
                                it.id.toString(),
                                context
                            )
                            _user.value = Result.Success(it)
                        }
                    }
                }
            }else {
                throw HttpException(client)
            }
        }catch (e: HttpException) {
            Log.e("HTTP_EXCEPTION: ", e.message())
            _user.value = Result.Error(e.message())
        }

        return _user.asStateFlow()
    }

    suspend fun forgotPassword(email: String): Flow<Result<String>> {
        _forgotPasswordState.value = Result.Loading()
        try {
            val client = apiService.forgotPassword(email)
            if (client.isSuccessful) {
                val message = client.body()?.message
                message?.let {
                    _forgotPasswordState.value = Result.Success("Reset password link has been sent to your email")
                }
            } else {
                throw HttpException(client)
            }
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, SignInResponse::class.java)
            val errorMessage = errorBody.message
            _user.value = Result.Error(errorMessage.toString())
        }

        return _forgotPasswordState.asStateFlow()
    }

    fun isUserSignedIn(): Boolean {
        val sharedPref = Preference.init(context, "session")
        val name = sharedPref.getString(USER_ID, "")

        return name != ""
    }

    fun getSignedInUser(): UserModel {
        val sharedPref = Preference.init(context, "session")
        val userId = sharedPref.getString(USER_ID, "")
        val name = sharedPref.getString(NAME, "")
        val email = sharedPref.getString(EMAIL, "")
        val avatar = sharedPref.getString(AVATAR, "")

        return UserModel(
            id = userId,
            name = name,
            email = email,
            avatar = avatar
        )
    }

    fun logOut() {
        Preference.logOut(context)
    }
}