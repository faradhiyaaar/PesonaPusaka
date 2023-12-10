package com.capstone.pesonapusaka.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Candi(
    val fotoCandi: String? = null,
    val namaCandi: String? = null,
    val lokasiCandi: String? = null,
    val sejarah: String? = null,
    val lat: Float? = null,
    val lng: Float? = null
): Parcelable