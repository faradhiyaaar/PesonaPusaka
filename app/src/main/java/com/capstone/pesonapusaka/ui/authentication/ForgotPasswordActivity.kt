package com.capstone.pesonapusaka.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.ActivityForgotPasswordBinding
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.show
import com.capstone.pesonapusaka.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val viewModel by viewModels<AuthViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()

        binding.btnForgotPassword.setOnClickListener {
            if (binding.etEmail.text.toString().isEmpty()) {
                toast("Harap isi email")
                return@setOnClickListener
            }

            viewModel.forgotPassword(binding.etEmail.text.toString())
        }
    }

    private fun observer() {
        val forgot = viewModel.forgotPasswordState.asLiveData()
        forgot.observe(this) {
            when (it) {
                is Result.Error -> {
                    binding.progressBar.hide()
                    toast(it.error.toString())
                }
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    toast("New password sent successfully")
                    finish()
                }
                is Result.Void -> {}
            }
        }
    }
}