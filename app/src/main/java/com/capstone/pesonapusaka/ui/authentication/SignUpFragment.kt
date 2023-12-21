package com.capstone.pesonapusaka.ui.authentication

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.databinding.FragmentSignUpBinding
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.toast
import com.capstone.pesonapusaka.utils.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()
    private var imageFile: File ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGallery.setOnClickListener {
            val chooser = setUpGallery()
            launchGallery.launch(chooser)
        }

        with(binding) {
            btnSignup.setOnClickListener {
                val name = etNama.text.toString()
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (imageFile == null && name.isEmpty() && email.isEmpty() && password.isEmpty()) {
                    toast("Harap isi semua fields")
                    return@setOnClickListener
                }
                imageFile?.let {
                    viewModel.register(
                        it,
                        name,
                        email,
                        password
                    )
                }
            }
        }

        observer()
    }

    private fun observer() {
        val registerState = viewModel.registerState.asLiveData()
        registerState.observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> {
                    binding.progressBar.hide()
                    toast(it.error.toString())
                }
                is Result.Loading -> {
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    toast("Register sukses, silahkan login")
                }
                is Result.Void -> {

                }

                else -> {}
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
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, requireContext())
                imageFile = myFile
                binding.ivProfil.setImageURI(uri)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}