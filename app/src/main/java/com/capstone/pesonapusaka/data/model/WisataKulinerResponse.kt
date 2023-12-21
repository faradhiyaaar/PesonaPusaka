package com.capstone.pesonapusaka.data.model

import com.google.gson.annotations.SerializedName

data class WisataKulinerResponse(
    val status: String? = null,
    val message: String? = null,
    val data: List<WisataKuliner>? = null
)

data class WisataKulinerDetailResponse(
    val status: String? = null,
    val message: String? = null,
    val data: WisataKuliner? = null
)

data class WisataKuliner(
    val id: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val thumbnail: String? = null,
    val location: String? = null,
    val direction: String? = null,
    @field:SerializedName("long_description")
    val longDescription: String? = null,
    @field:SerializedName("is_recommended")
    val isRecommended: String? = null,
    val provinsi: String? = null,
    val kabupaten: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)