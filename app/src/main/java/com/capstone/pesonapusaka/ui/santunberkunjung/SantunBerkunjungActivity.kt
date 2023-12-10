package com.capstone.pesonapusaka.ui.santunberkunjung

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.databinding.ActivitySantunBerkunjungBinding

class SantunBerkunjungActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySantunBerkunjungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySantunBerkunjungBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = getColor(R.color.black20)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}