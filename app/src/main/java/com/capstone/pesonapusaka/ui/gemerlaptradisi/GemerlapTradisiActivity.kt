package com.capstone.pesonapusaka.ui.gemerlaptradisi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.databinding.ActivityGemerlapTradisiBinding
import com.capstone.pesonapusaka.ui.gemerlaptradisi.adapter.TradisiAdapter
import com.capstone.pesonapusaka.utils.Dimens.LOREM

class GemerlapTradisiActivity : AppCompatActivity() {

    private var _binding: ActivityGemerlapTradisiBinding? = null
    private val binding get() = _binding!!
    private val tradisiAdapter by lazy { TradisiAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGemerlapTradisiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvTradisi.apply {
            adapter = tradisiAdapter
            layoutManager = LinearLayoutManager(this@GemerlapTradisiActivity)
        }

        val listTradisi = mutableListOf<Tradisi>()
        val tradisi =
            Tradisi(
                namaTradisi = "Upacara Angayuba",
                tanggalTradisi = "12 Agustus",
                lokasiTradisi = "Candi Prambanan",
                deskripsiTradisi = LOREM
            )
        for (i in 0..10) {
            listTradisi.add(tradisi)
        }

        tradisiAdapter.differ.submitList(listTradisi)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}