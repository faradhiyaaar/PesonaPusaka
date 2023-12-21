package com.capstone.pesonapusaka.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class CandiResponse(
    @field:SerializedName("data")
    val data: List<CandiModel>? = null,
    @field:SerializedName("status")
    val status: String? = null,
    @field:SerializedName("message")
    val message: String? = null,
)

data class DetailCandiResponse(
    val status: String? = null,
    val message: String? = null,
    val data: CandiModel? = null
)

@Parcelize
data class CandiModel(
    @field:SerializedName("direction")
    val direction: String? = null,
    @field:SerializedName("history")
    val history: String? = null,
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("is_recommended")
    val isRecommended: String? = null,
    @field:SerializedName("kabupaten")
    val kabupaten: String? = null,
    @field:SerializedName("latitude")
    val latitude: Double? = null,
    @field:SerializedName("location")
    val location: String? = null,
    @field:SerializedName("longitude")
    val longitude: Double? = null,
    @field:SerializedName("name")
    val name: String? = null,
    @field:SerializedName("provinsi")
    val provinsi: String? = null,
    @field:SerializedName("rating")
    val rating: Double? = null,
    @field:SerializedName("thumbnail")
    val thumbnail: String? = null
): Parcelable