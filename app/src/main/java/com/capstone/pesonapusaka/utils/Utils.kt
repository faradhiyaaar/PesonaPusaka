package com.capstone.pesonapusaka.utils

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}