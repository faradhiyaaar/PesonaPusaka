package com.capstone.pesonapusaka.ui.wisatacandi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.ActivityWisataCandiBinding
import com.capstone.pesonapusaka.ui.wisatacandi.adapter.WisataCandiAdapter
import com.capstone.pesonapusaka.utils.Dimens.LOREM

class WisataCandiActivity : AppCompatActivity() {

    private var _binding: ActivityWisataCandiBinding? = null
    private val binding get() = _binding!!
    private val candiAdapter by lazy { WisataCandiAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWisataCandiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        binding.rvCandi.apply {
            adapter = candiAdapter
            layoutManager = LinearLayoutManager(this@WisataCandiActivity)
        }

        val listCandiDummy = mutableListOf<Candi>()
        val candi = Candi(
            namaCandi = "Candi Ratu Boko",
            lokasiCandi = "Daerah Istimewa Yogyakarta",
            sejarah = LOREM
        )
        for (i in 0..10) {
            listCandiDummy.add(candi)
        }

        candiAdapter.differ.submitList(listCandiDummy)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}