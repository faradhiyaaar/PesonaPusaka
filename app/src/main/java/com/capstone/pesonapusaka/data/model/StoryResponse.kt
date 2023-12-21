package com.capstone.pesonapusaka.data.model

import com.google.gson.annotations.SerializedName

data class StoryResponse(
    val status: String? = null,
    val message: String? = null,
    val data: List<StoryModel>? = null
)

data class StoryDetailResponse(
    val status: String? = null,
    val message: String? = null,
    val data: StoryModel? = null
)

data class StoryModel(
    val id: Int? = null,
    val idUser: Int? = null,
    val title: String? = null,
    val image: String? = null,
    val timeStamp: Int? = null,
    val content: String? = null,
    val name: String? = null,
    val email: String? = null,
    val avatar: String? = null
)