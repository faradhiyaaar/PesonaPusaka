package com.capstone.pesonapusaka.utils

import android.Manifest

object Dimens {
    const val BASE_URL = "https://pesona-pusaka-api.uc.r.appspot.com/api/"
    const val ML_BASE_URL = "https://ml-dot-pesona-pusaka-api.uc.r.appspot.com/api/"
    const val TRADISI = "tradisi"
    const val CANDI = "candi"
    const val LOCATION = "location"
    const val NAMA_LOKASI = "nama_lokasi"
    const val SESSION = "session"
    const val USER_ID = "user_id"
    const val NAME = "name"
    const val AVATAR = "avatar"
    const val EMAIL = "email"
    const val FAVOURITE_DB = "favdb"
    val PERMISSIONS = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )
}