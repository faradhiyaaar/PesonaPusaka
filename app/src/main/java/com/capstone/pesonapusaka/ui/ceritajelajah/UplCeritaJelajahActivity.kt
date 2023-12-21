package com.capstone.pesonapusaka.ui.ceritajelajah

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.databinding.ActivityUplCeritaJelajahBinding
import com.capstone.pesonapusaka.utils.Dimens.NAME
import com.capstone.pesonapusaka.utils.Dimens.PERMISSIONS
import com.capstone.pesonapusaka.utils.Dimens.USER_ID
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.pickPhoto
import com.capstone.pesonapusaka.utils.setUpCamera
import com.capstone.pesonapusaka.utils.show
import com.capstone.pesonapusaka.utils.showPermissionSettingsAlert
import com.capstone.pesonapusaka.utils.toast
import com.capstone.pesonapusaka.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class UplCeritaJelajahActivity : AppCompatActivity() {

    private var _binding: ActivityUplCeritaJelajahBinding? = null
    private val viewModel by viewModels<CeritaJelajahViewModel>()
    private val binding get() = _binding!!
    private var photoPath: String? = null
    private var imageFile: File? = null
    private var permissionGiven: Boolean? = null
    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            val isGranted = permissions[Manifest.permission.CAMERA] ?: false
            val readData = permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: false

            if (isGranted && readData) {
                permissionGiven = true
            }
        }

    private fun permissionSet() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionGiven = true
        } else {
            permission.launch(PERMISSIONS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUplCeritaJelajahBinding.inflate(layoutInflater)
        setContentView(binding.root)
        permissionSet()

        binding.cvTakePicture.setOnClickListener {
            pickPhoto(
                onCameraClick = {
                    permissionGiven?.let {
                        if (it) {
                            startCamera()
                        } else {
                            showPermissionSettingsAlert(this)
                        }
                    }
                },
                onGalleryClick = {
                    val chooser = setUpGallery()
                    launchGallery.launch(chooser)
                }
            )
        }
        with(binding) {
            btnUpload.setOnClickListener {
                val content = binding.etStory.text.toString()
                if (imageFile == null && content.isEmpty()) {
                    toast("Harap isi foto dan deskripsi")
                    return@setOnClickListener
                }

                val user = viewModel.getUser()
                user.id?.let { id ->
                    Log.e("IDDDD", id)
                    imageFile?.let {
                        viewModel.uploadStory(
                            it, content, user.name!!, id
                        )
                    }
                }
            }
        }

        observer()
    }

    private fun observer() {
        val postState = viewModel.postStory.asLiveData()
        postState.observe(this) {
            when(it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    toast(it.data ?: "Berhasil tambah cerita")
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.hide()
                    toast(it.error.toString())
                }
                is Result.Void -> {

                }
            }
        }
    }

    private fun startCamera() {
        val intent = setUpCamera()
        photoPath = intent.path
        launchCamera.launch(intent.intent)
    }

    private val launchCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(photoPath!!)

            myFile.let { file ->
                imageFile = file
                binding.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private fun setUpGallery(): Intent {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        return Intent.createChooser(intent, "Choose a Picture")
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this)
                imageFile = myFile
                binding.ivPhoto.setImageURI(uri)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}