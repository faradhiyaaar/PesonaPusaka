package com.capstone.pesonapusaka.ui.ceritajelajah

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Story
import com.capstone.pesonapusaka.databinding.ActivityCeritaJelajahBinding
import com.capstone.pesonapusaka.ui.ceritajelajah.adapter.StoryAdapter

class CeritaJelajahActivity : AppCompatActivity() {

    private var _binding: ActivityCeritaJelajahBinding? = null
    private val binding get() = _binding!!
    private val storyAdapter by lazy { StoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCeritaJelajahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setRecyclerView()

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun setRecyclerView() {
        binding.rvStory.apply {
            adapter = storyAdapter
            layoutManager = LinearLayoutManager(this@CeritaJelajahActivity)
        }

        val listStoryDummy = mutableListOf<Story>()
        val story = Story(
            nama = "Dina",
            tanggal = "14 November 2023 - 08.30",
            story = getString(R.string.lorem)
        )
        for (i in 0..10) {
            listStoryDummy.add(story)
        }

        storyAdapter.differ.submitList(listStoryDummy)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}