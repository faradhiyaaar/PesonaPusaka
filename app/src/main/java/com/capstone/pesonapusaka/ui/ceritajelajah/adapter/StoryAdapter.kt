package com.capstone.pesonapusaka.ui.ceritajelajah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Story
import com.capstone.pesonapusaka.databinding.ItemStoryBinding

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(story: Story) {
                with(binding) {
                    tvStory.text = story.story
                    tvNamaUser.text = story.nama
                    tvTanggalStory.text = story.tanggal
                    ivStory.setImageResource(R.drawable.iv_candi)
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Story>() {
        override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem.nama == newItem.nama
        }

        override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = differ.currentList[position]
        holder.bind(story)
    }
}