package com.capstone.pesonapusaka.ui.ceritajelajah

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.StoryModel
import com.capstone.pesonapusaka.databinding.ActivityCeritaJelajahBinding
import com.capstone.pesonapusaka.ui.ceritajelajah.adapter.StoryAdapter
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.show
import com.capstone.pesonapusaka.utils.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CeritaJelajahActivity : AppCompatActivity() {

    private var _binding: ActivityCeritaJelajahBinding? = null
    private val binding get() = _binding!!
    private val storyAdapter by lazy { StoryAdapter() }
    private val viewModel by viewModels<CeritaJelajahViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCeritaJelajahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()

        binding.btnBack.setOnClickListener {
            finish()
        }
        binding.btnMakeStory.setOnClickListener {
            startActivity(
                Intent(this, UplCeritaJelajahActivity::class.java)
            )
        }
    }

    private fun setRecyclerView(listStory: List<StoryModel>) {
        binding.rvStory.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(this@CeritaJelajahActivity)
        }
        storyAdapter.differ.submitList(listStory)
    }

    private fun observer() {
        val stories = viewModel.stories.asLiveData()
        stories.observe(this) { result ->
            when (result) {
                is Result.Loading -> {
                    binding.progressBar.show()
                }
                is Result.Success -> {
                    binding.progressBar.hide()
                    result.data?.let {
                        setRecyclerView(it)
                    }
                }
                is Result.Error -> {
                    binding.progressBar.hide()
                    toast(result.error.toString())
                }
                is Result.Void -> {}
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}