package com.capstone.pesonapusaka.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tradisi(
    val namaTradisi: String? = null,
    val fotoTradisi: String? = null,
    val tanggalTradisi: String? = null,
    val deskripsiTradisi: String? = null,
    val lokasiTradisi: String? = null
): Parcelable
