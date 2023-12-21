package com.capstone.pesonapusaka.data.local

import android.content.Context
import android.content.SharedPreferences
import com.capstone.pesonapusaka.utils.Dimens.AVATAR
import com.capstone.pesonapusaka.utils.Dimens.EMAIL
import com.capstone.pesonapusaka.utils.Dimens.NAME
import com.capstone.pesonapusaka.utils.Dimens.SESSION
import com.capstone.pesonapusaka.utils.Dimens.USER_ID

object Preference {
    fun init(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun preferenceEditor(context: Context): SharedPreferences.Editor {
        val sharedPref = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE)
        return sharedPref.edit()
    }

    fun saveToken(name: String, email: String,avatar: String, userId: String, context: Context) {
        val editor = preferenceEditor(context)
        editor.putString(USER_ID, userId)
        editor.putString(NAME, name)
        editor.putString(EMAIL, email)
        editor.putString(AVATAR, avatar)
        editor.apply()
    }

    fun logOut(context: Context) {
        val editor = preferenceEditor(context)
        editor.remove(USER_ID)
        editor.remove(NAME)
        editor.remove(EMAIL)
        editor.remove(AVATAR)
        editor.remove("status")
        editor.apply()
    }
}