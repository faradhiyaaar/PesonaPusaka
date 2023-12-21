@file:Suppress("DEPRECATION")

package com.capstone.pesonapusaka.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.pesonapusaka.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun Fragment.toast(msg: String) {
    Toast.makeText(requireActivity(), msg, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.glide(url: String) {
    Glide.with(this.context).load(url).into(this)
}

fun getInterceptor(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
}

@SuppressLint("InflateParams", "MissingInflatedId")
fun Activity.pickPhoto(
    onCameraClick: () -> Unit?,
    onGalleryClick: () -> Unit?
) {
    val dialog = Dialog(this, android.R.style.Theme_Dialog)
    val view = layoutInflater.inflate(R.layout.pick_photo_dialog, null)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(view)
    dialog.window?.setGravity(Gravity.BOTTOM)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()

    val btnCamera = view.findViewById<LinearLayout>(R.id.layout_camera)
    val btnGallery = view.findViewById<LinearLayout>(R.id.layout_gallery)

    btnCamera.setOnClickListener {
        onCameraClick()
        dialog.dismiss()
    }

    btnGallery.setOnClickListener {
        onGalleryClick()
        dialog.dismiss()
    }
}

@SuppressLint("InflateParams")
fun Fragment.pickLanguage(
    onLanguagePick: (String) -> Unit
) {
    val dialog = Dialog(requireContext(), android.R.style.Theme_Dialog)
    val view = layoutInflater.inflate(R.layout.pick_language, null)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    dialog.setContentView(view)
    dialog.window?.setGravity(Gravity.BOTTOM)
    dialog.window?.setLayout(
        WindowManager.LayoutParams.MATCH_PARENT,
        WindowManager.LayoutParams.WRAP_CONTENT
    )
    dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog.show()

    val btnIndo = view.findViewById<LinearLayout>(R.id.layout_indonesia)
    val btnEnglish = view.findViewById<LinearLayout>(R.id.layout_english)

    btnIndo.setOnClickListener {
        onLanguagePick("in")
        dialog.dismiss()
    }

    btnEnglish.setOnClickListener {
        onLanguagePick("en")
        dialog.dismiss()
    }
}

fun Fragment.setAppLocale(languageFromPreference: String?, context: Context)
{

    if (languageFromPreference != null) {
        val resources = context.resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(Locale(languageFromPreference.toLowerCase(Locale.ROOT)))
        resources.updateConfiguration(config, dm)

        val sharedPref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("languange", languageFromPreference)
        editor.apply()
    }
}

fun reduceFileImage(file: File): File {
    val bitmap = BitmapFactory.decodeFile(file.path)
    var compressQuality = 100
    var streamLength: Int
    do {
        val bmpStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
        val bmpPicByteArray = bmpStream.toByteArray()
        streamLength = bmpPicByteArray.size
        compressQuality -= 5
    } while (streamLength > 1000000)

    bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
    return file
}

fun showPermissionSettingsAlert(context: Context) {
    val builder = AlertDialog.Builder(context)
    builder.setTitle("Grant Permission")
    builder.setMessage("Izinkan aplikasi untuk mengakses kamera untuk melanjutkan")
    builder.setPositiveButton("Allow") { _, _ ->
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
    builder.setNeutralButton("Deny") { dialog, _ ->
        dialog.dismiss()
    }

    val dialog = builder.create()
    dialog.show()
}

@SuppressLint("QueryPermissionsNeeded")
fun Activity.setUpCamera(): Camera {
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    var photoPath: String?
    this.let { activity ->
        intent.resolveActivity(activity.packageManager)
        createTemporaryFile(this).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
                "com.capstone.pesonapusaka.ui.ceritajelajah",
                it
            )
            photoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        }
    }
    return Camera(photoPath!!, intent)
}

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())
fun createTemporaryFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTemporaryFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}

data class Camera(
    val path: String,
    val intent: Intent
)