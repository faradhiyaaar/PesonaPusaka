package com.capstone.pesonapusaka.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.ActivityOnboardingBinding
import com.capstone.pesonapusaka.ui.authentication.AuthActivity
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.show

class OnboardingActivity : AppCompatActivity() {

    private var _binding: ActivityOnboardingBinding? = null
    private val binding get() = _binding!!
    private lateinit var onboardingAdapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        setupOnBoardingItems()
        binding.viewPager2.adapter = onboardingAdapter

        setupOnBoardingItems()
        setupBoardingIndicators()
        setActiveIndicators(0)

        binding.viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setActiveIndicators(position)
            }
        })

        binding.btnNext.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, AuthActivity::class.java)
            startActivity(intent)
            onBoardingFinished()
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupOnBoardingItems() {
        val onboardingItems: MutableList<OnBoardingItem> = ArrayList()

        val onboarding1 = OnBoardingItem(
            resources.getString(R.string.onboarding_1),
            R.drawable.onboarding_1
        )
        val onboarding2 = OnBoardingItem(
            resources.getString(R.string.onboarding_2),
            R.drawable.onboarding_2
        )
        val onboarding3 = OnBoardingItem(
            resources.getString(R.string.onboarding_3),
            R.drawable.onboarding_3
        )
        onboardingItems.add(onboarding1)
        onboardingItems.add(onboarding2)
        onboardingItems.add(onboarding3)
        onboardingAdapter = OnboardingAdapter()
        onboardingAdapter.setNewItem(onboardingItems)
    }

    private fun setupBoardingIndicators() {
        val indicators = arrayOfNulls<ImageView>(onboardingAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8,0,8,0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.viewpagerIndicator.addView(indicators[i])
        }
    }

    private fun setActiveIndicators(index: Int) {
        val childCount = binding.viewpagerIndicator.childCount
        for (i in 0 until childCount) {
            val imageView = binding.viewpagerIndicator[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
        if (index == onboardingAdapter.itemCount -1) {
            binding.btnNext.show()
        }else {
            binding.btnNext.hide()
        }
    }

    private fun onBoardingFinished() {
        val sharedPref = getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("OnBoarding", true)
        editor.apply()
    }
}