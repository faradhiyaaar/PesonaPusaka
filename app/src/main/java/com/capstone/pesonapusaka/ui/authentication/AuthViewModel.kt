package com.capstone.pesonapusaka.ui.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.pesonapusaka.data.model.UserModel
import com.capstone.pesonapusaka.data.repository.AuthRepository
import com.capstone.pesonapusaka.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _loginState = MutableStateFlow<Result<UserModel>>(Result.Void())
    val loginState = _loginState.asStateFlow()

    private val _registerState = MutableStateFlow<Result<String>>(Result.Void())
    val registerState = _registerState.asStateFlow()

    private val _forgotPasswordState = MutableStateFlow<Result<String>>(Result.Void())
    val forgotPasswordState = _forgotPasswordState.asStateFlow()

    private val _user = MutableStateFlow(false)
    val user = _user.asStateFlow()

    init {
        viewModelScope.launch {
            _user.value = authRepository.isUserSignedIn()
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signIn(email, password).collect {
                _loginState.value = it
            }
        }
    }

    fun register(image: File, name: String, email: String, password: String) {
        viewModelScope.launch {
            authRepository.register(
                image, name, email, password
            ).collect {
                _registerState.value = it
            }
        }
    }

    fun forgotPassword(email: String) {
        viewModelScope.launch {
            authRepository.forgotPassword(email).collect {
                _forgotPasswordState.value = it
            }
        }
    }
}