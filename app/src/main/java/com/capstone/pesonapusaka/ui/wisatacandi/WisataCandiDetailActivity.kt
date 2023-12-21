package com.capstone.pesonapusaka.ui.wisatacandi

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.Location
import com.capstone.pesonapusaka.databinding.ActivityWisataCandiDetailBinding
import com.capstone.pesonapusaka.ui.home.adapter.GemerlapTradisiAdapter
import com.capstone.pesonapusaka.ui.maps.MapsActivity
import com.capstone.pesonapusaka.ui.wisatakuliner.WisataKulinerActivity
import com.capstone.pesonapusaka.utils.Dimens
import com.capstone.pesonapusaka.utils.Dimens.LOCATION
import com.capstone.pesonapusaka.utils.Dimens.NAMA_LOKASI
import com.capstone.pesonapusaka.utils.glide
import com.capstone.pesonapusaka.utils.listTradisi

@Suppress("DEPRECATION")
class WisataCandiDetailActivity : AppCompatActivity() {

    private var _binding: ActivityWisataCandiDetailBinding? = null
    private val binding get() = _binding!!
    private val gemerlapTradisiAdapter by lazy { GemerlapTradisiAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWisataCandiDetailBinding.inflate(layoutInflater)
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
            intent.getParcelableExtra(Dimens.CANDI, CandiModel::class.java)
        } else {
            intent.getParcelableExtra(Dimens.CANDI)
        }

        with(binding) {
            data?.let { candi ->
                tvNamaCandi.text = candi.name
                tvLokasiCandi.text = candi.location
                tvSejarah.text = candi.history
                candi.thumbnail?.let {
                    imageView.glide(it)
                }

                btnDirection.setOnClickListener {
                    if (candi.latitude != null) {
                        startActivity(
                            Intent(this@WisataCandiDetailActivity, MapsActivity::class.java).also {
                                it.putExtra(LOCATION, Location(candi.latitude, candi.longitude!!))
                                it.putExtra(NAMA_LOKASI, candi.name)
                            }
                        )
                    } else {
                        Toast.makeText(
                            this@WisataCandiDetailActivity,
                            "No location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            btnBack.setOnClickListener {
                finish()
            }

            btnFoodRecommendation.setOnClickListener {
                startActivity(
                    Intent(this@WisataCandiDetailActivity, WisataKulinerActivity::class.java)
                )
                finish()
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvGemerlapTradisi.apply {
            adapter = gemerlapTradisiAdapter
            layoutManager = LinearLayoutManager(
                this@WisataCandiDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        gemerlapTradisiAdapter.differ.submitList(listTradisi)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}