package com.capstone.pesonapusaka.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.FragmentProfileBinding
import com.capstone.pesonapusaka.ui.authentication.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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