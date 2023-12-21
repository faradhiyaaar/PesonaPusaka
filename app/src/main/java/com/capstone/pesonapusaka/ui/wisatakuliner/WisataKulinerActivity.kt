package com.capstone.pesonapusaka.ui.wisatakuliner

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.databinding.ActivityWisataKulinerBinding
import com.capstone.pesonapusaka.ui.wisatakuliner.adapter.WisataKulinerAdapter
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WisataKulinerActivity : AppCompatActivity() {

    private var _binding: ActivityWisataKulinerBinding? = null
    private val binding get() = _binding!!
    private val wisataKulinerAdapter by lazy { WisataKulinerAdapter() }
    private val viewModel by viewModels<WisataKulinerViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWisataKulinerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView(wisataKuliner: List<WisataKuliner>?) {
        binding.rvWisataKuliner.apply {
            adapter = wisataKulinerAdapter
            layoutManager = LinearLayoutManager(this@WisataKulinerActivity)
        }
        wisataKuliner?.let { list ->
            wisataKulinerAdapter.differ.submitList(list)

            wisataKulinerAdapter.onClick = {
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
        val wisataKuliner = viewModel.wisataKuliner.asLiveData()
        wisataKuliner.observe(this) {
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