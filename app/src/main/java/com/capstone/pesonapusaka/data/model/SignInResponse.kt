package com.capstone.pesonapusaka.data.model

data class SignInResponse(
    val status: String? = null,
    val message: String? = null,
    val data: UserModel? = null
)

data class PostResponse(
    val status: String? = null,
    val message: String? = null,
)

data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val avatar: String? = null
)