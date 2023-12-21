package com.capstone.pesonapusaka.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.MainActivity
import com.capstone.pesonapusaka.databinding.FragmentSignInBinding
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.show
import com.capstone.pesonapusaka.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()

        binding.btnSignin.setOnClickListener {
            onSignIn()
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(
                Intent(requireContext(), ForgotPasswordActivity::class.java)
            )
        }
    }

    private fun onSignIn() {
        binding.apply {
            if (etEmail.text.toString().isNotEmpty() && etPassword.text.toString().isNotEmpty()) {
                viewModel.signIn(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            }else {
                toast("Please fill all fields")
            }
        }
    }

    private fun observer() {
        val loginState = viewModel.loginState.asLiveData()
        loginState.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    startActivity(
                        Intent(requireActivity(), MainActivity::class.java)
                    )
                    requireActivity().finish()
                }
                is Result.Error -> {
                    binding.progressBar.hide()
                    toast(it.error.toString())
                }
                else -> {}
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}