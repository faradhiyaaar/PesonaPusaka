package com.capstone.pesonapusaka.ui.ceritajelajah

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pesonapusaka.data.model.StoryModel
import com.capstone.pesonapusaka.data.model.UserModel
import com.capstone.pesonapusaka.data.repository.AuthRepository
import com.capstone.pesonapusaka.data.repository.Repository
import com.capstone.pesonapusaka.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

@HiltViewModel
class CeritaJelajahViewModel @Inject constructor(
    private val repository: Repository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _stories = MutableStateFlow<Result<List<StoryModel>>>(Result.Void())
    val stories = _stories.asStateFlow()

    private val _postStory = MutableStateFlow<Result<String>>(Result.Void())
    val postStory = _postStory.asStateFlow()

    private var user = UserModel()
    init {
        getAllStories()

        viewModelScope.launch {
            user = authRepository.getSignedInUser()
        }
    }

    fun uploadStory(
        image: File,
        description: String,
        name: String,
        id: String
    ) {
        viewModelScope.launch {
            repository.postStory(
                image,
                description,
                name,
                "4"
            ).collect {
                _postStory.value = it
            }
        }
    }

    fun getUser(): UserModel = authRepository.getSignedInUser()

    private fun getAllStories() {
        viewModelScope.launch {
            repository.getAllStories().collect {
                _stories.value = it
            }
        }
    }
}