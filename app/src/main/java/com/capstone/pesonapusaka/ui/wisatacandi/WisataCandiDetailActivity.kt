package com.capstone.pesonapusaka.ui.wisatacandi

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.databinding.ActivityWisataCandiDetailBinding
import com.capstone.pesonapusaka.ui.home.adapter.GemerlapTradisiAdapter
import com.capstone.pesonapusaka.ui.wisatacandi.adapter.PreviewAdapter
import com.capstone.pesonapusaka.utils.Dimens

@Suppress("DEPRECATION")
class WisataCandiDetailActivity : AppCompatActivity() {

    private var _binding: ActivityWisataCandiDetailBinding? = null
    private val binding get() = _binding!!
    private val previewAdapter by lazy { PreviewAdapter() }
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
            intent.getParcelableExtra(Dimens.CANDI, Candi::class.java)
        } else {
            intent.getParcelableExtra(Dimens.CANDI)
        }

        with(binding) {
            data?.let { candi ->
                tvNamaCandi.text = candi.namaCandi
                tvLokasiCandi.text = candi.lokasiCandi
                tvSejarah.text = candi.sejarah
            }

            btnBack.setOnClickListener {
                finish()
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvPreview.apply {
            adapter = previewAdapter
            layoutManager = LinearLayoutManager(
                this@WisataCandiDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        val listCandiDummy = mutableListOf<Candi>()
        val candi = Candi(namaCandi = "Candi Ratu Boko", lokasiCandi = "Daerah Istimewa Yogyakarta")
        for (i in 0..10) {
            listCandiDummy.add(candi)
        }

        previewAdapter.differ.submitList(listCandiDummy)

        binding.rvGemerlapTradisi.apply {
            adapter = gemerlapTradisiAdapter
            layoutManager = LinearLayoutManager(
                this@WisataCandiDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        val listTradisi = mutableListOf<Tradisi>()
        val tradisi = Tradisi(namaTradisi = "Upacara Angayuba")
        for (i in 0..10) {
            listTradisi.add(tradisi)
        }

        gemerlapTradisiAdapter.differ.submitList(listTradisi)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}