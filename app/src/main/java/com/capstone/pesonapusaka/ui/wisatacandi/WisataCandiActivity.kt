package com.capstone.pesonapusaka.ui.wisatacandi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.databinding.ActivityWisataCandiBinding
import com.capstone.pesonapusaka.ui.wisatacandi.adapter.WisataCandiAdapter
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WisataCandiActivity : AppCompatActivity() {

    private var _binding: ActivityWisataCandiBinding? = null
    private val binding get() = _binding!!
    private val candiAdapter by lazy { WisataCandiAdapter() }
    private val viewModel by viewModels<WisataCandiViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWisataCandiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView(candi: List<CandiModel>?) {
        binding.rvCandi.apply {
            adapter = candiAdapter
            layoutManager = LinearLayoutManager(this@WisataCandiActivity)
        }
        candi?.let { candiModels ->
            candiAdapter.differ.submitList(candiModels)

            candiAdapter.onClick = {
                viewModel.addToFavorite(
                    Favorite(
                        name = it.name!!,
                        location = it.location!!,
                        thumbnail = it.thumbnail!!
                    )
                )
                toast("Berhasil menambahkan favorite!")
            }
        }
    }

    private fun observer() {
        val candi = viewModel.candi.asLiveData()
        candi.observe(this) {
            when(it) {
                is Result.Error -> {

                }
                is Result.Loading -> {

                }
                is Result.Success -> {
                    setupRecyclerView(it.data)
                }
                is Result.Void -> {

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}