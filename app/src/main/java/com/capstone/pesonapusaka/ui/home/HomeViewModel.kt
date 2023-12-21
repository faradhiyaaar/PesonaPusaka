package com.capstone.pesonapusaka.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.UserModel
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.data.repository.AuthRepository
import com.capstone.pesonapusaka.data.repository.Repository
import com.capstone.pesonapusaka.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    private val authRepository: AuthRepository
): ViewModel() {
    private val _candiRecommendation = MutableStateFlow<Result<List<CandiModel>>>(Result.Void())
    val candiRecommendation = _candiRecommendation.asStateFlow()
    private val _wisataKuliner = MutableStateFlow<Result<List<WisataKuliner>>>(Result.Void())
    val wisataKuliner = _wisataKuliner.asStateFlow()
    private val _user = MutableStateFlow(UserModel())
    val user = _user.asStateFlow()

    init {
        getCandiRecommendation()
        getUmkmRecommendation()
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            _user.value = authRepository.getSignedInUser()
        }
    }

    private fun getUmkmRecommendation() {
        viewModelScope.launch {
            repository.getWisataKuliner().collect {
                _wisataKuliner.value = it
            }
        }
    }

    private fun getCandiRecommendation() {
        viewModelScope.launch {
            repository.getCandiRecommendation().collect {
                _candiRecommendation.value = it
            }
        }
    }

    fun logOut() {
        authRepository.logOut()
    }
}