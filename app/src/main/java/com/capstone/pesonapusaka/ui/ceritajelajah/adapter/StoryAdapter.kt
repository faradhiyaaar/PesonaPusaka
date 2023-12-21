package com.capstone.pesonapusaka.ui.ceritajelajah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.StoryModel
import com.capstone.pesonapusaka.databinding.ItemStoryBinding
import com.capstone.pesonapusaka.utils.glide

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    inner class StoryViewHolder(private val binding: ItemStoryBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(story: StoryModel) {
                with(binding) {
                    tvStory.text = story.content
                    tvNamaUser.text = story.name
                    tvTanggalStory.text = story.email
                    story.image?.let {
                        ivStory.glide(it)
                    }
                    story.avatar?.let {
                        ivAvatar.glide(it)
                    }
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<StoryModel>() {
        override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
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