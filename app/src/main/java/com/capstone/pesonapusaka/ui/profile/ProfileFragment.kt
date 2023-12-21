package com.capstone.pesonapusaka.ui.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.FragmentProfileBinding
import com.capstone.pesonapusaka.ui.authentication.adapter.ViewPagerAdapter
import com.capstone.pesonapusaka.ui.home.HomeViewModel
import com.capstone.pesonapusaka.ui.onboarding.OnboardingActivity
import com.capstone.pesonapusaka.utils.glide
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
        observer()

        binding.btnLogOut.setOnClickListener {
            val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("OnBoarding", false)
            editor.apply()
            viewModel.logOut()
            startActivity(
                Intent(requireContext(), OnboardingActivity::class.java)
            )
            requireActivity().finish()
        }
    }

    private fun observer() {
        val user = viewModel.user.asLiveData()
        user.observe(viewLifecycleOwner) { userModel ->
            userModel.avatar?.let {
                binding.ivProfil.glide(it)
            }
            binding.tvName.text = userModel.name
        }
    }

    private fun setupTabLayout() {
        val fragments = listOf(
            FavoriteFragment(),
            LanguageFragment()
        )
        binding.viewPager2.isUserInputEnabled = false
        val viewPagerAdapter = ViewPagerAdapter(fragments, requireActivity().supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { t, p ->
            when(p) {
                0 -> t.text = resources.getString(R.string.favorit)
                1 -> t.text = resources.getString(R.string.bahasa)
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}