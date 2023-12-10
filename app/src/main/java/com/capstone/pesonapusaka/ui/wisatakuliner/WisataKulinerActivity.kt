package com.capstone.pesonapusaka.ui.wisatakuliner

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.UMKM
import com.capstone.pesonapusaka.databinding.ActivityWisataKulinerBinding
import com.capstone.pesonapusaka.ui.wisatakuliner.adapter.WisataKulinerAdapter

class WisataKulinerActivity : AppCompatActivity() {

    private var _binding: ActivityWisataKulinerBinding? = null
    private val binding get() = _binding!!
    private val wisataKulinerAdapter by lazy { WisataKulinerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWisataKulinerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvWisataKuliner.apply {
            adapter = wisataKulinerAdapter
            layoutManager = LinearLayoutManager(this@WisataKulinerActivity)
        }

        val listUmkmDummy = mutableListOf<UMKM>()
        val umkm = UMKM(namaUmkm = "Warung Bakso Beranak", namaPenjualUmkm = "Bu Sri")
        for (i in 0..10) {
            listUmkmDummy.add(umkm)
        }

        wisataKulinerAdapter.differ.submitList(listUmkmDummy)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}