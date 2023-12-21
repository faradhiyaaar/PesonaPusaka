package com.capstone.pesonapusaka.ui.authentication

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import com.capstone.pesonapusaka.MainActivity
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.ActivityAuthBinding
import com.capstone.pesonapusaka.ui.authentication.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private var _binding: ActivityAuthBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        setupTabLayout()
    }

    private fun setupTabLayout() {
        val fragments = arrayListOf(
            SignInFragment(),
            SignUpFragment()
        )
        binding.viewPager2.isUserInputEnabled = false
        val viewPagerAdapter = ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        binding.viewPager2.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { t, p ->
            when(p) {
                0 -> t.text = resources.getString(R.string.signin)
                1 -> t.text = resources.getString(R.string.signup)
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        if (viewModel.user.value) {
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }
    }
}