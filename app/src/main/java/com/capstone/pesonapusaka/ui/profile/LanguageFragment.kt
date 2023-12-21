package com.capstone.pesonapusaka.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.pesonapusaka.databinding.FragmentLanguageBinding
import com.capstone.pesonapusaka.utils.pickLanguage
import com.capstone.pesonapusaka.utils.setAppLocale

class LanguageFragment : Fragment() {

    private lateinit var binding: FragmentLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLanguageBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireActivity().getSharedPreferences("language", Context.MODE_PRIVATE)
        val language = sharedPref.getString("language", "")

        when (language) {
            "en" -> {
                binding.tvLanguage.text = "English"
            }
            "in" -> {
                binding.tvLanguage.text = "Indonesia"
            }
            else -> {
                binding.tvLanguage.text = "Default"
            }
        }

        binding.cvLanguage.setOnClickListener {
            pickLanguage {
                if (it == "en") {
                    binding.tvLanguage.text = "English"
                    setAppLocale("en", requireContext())
                }else {
                    binding.tvLanguage.text = "Indonesia"
                    setAppLocale("id", requireContext())
                }
            }
        }
    }
}