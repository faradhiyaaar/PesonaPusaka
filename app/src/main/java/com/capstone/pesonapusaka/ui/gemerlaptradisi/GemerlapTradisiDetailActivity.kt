package com.capstone.pesonapusaka.ui.gemerlaptradisi

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.databinding.ActivityGemerlapTradisiDetailBinding
import com.capstone.pesonapusaka.utils.Dimens.TRADISI

@Suppress("DEPRECATION")
class GemerlapTradisiDetailActivity : AppCompatActivity() {

    private var _binding: ActivityGemerlapTradisiDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGemerlapTradisiDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        window.statusBarColor = getColor(R.color.black20)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }

        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(TRADISI, Tradisi::class.java)
        } else {
            intent.getParcelableExtra(TRADISI)
        }

        with(binding) {
            data?.let { tradisi ->
                tvNamaTradisi.text = tradisi.namaTradisi
                tvDeskripsiTradisi.text = tradisi.deskripsiTradisi
                tvLokasiTradisi.text = tradisi.lokasiTradisi
                tvTanggalTradisi.text = tradisi.tanggalTradisi
            }

            btnBack.setOnClickListener {
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}