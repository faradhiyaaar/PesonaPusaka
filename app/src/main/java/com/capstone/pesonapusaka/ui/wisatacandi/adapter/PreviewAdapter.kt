package com.capstone.pesonapusaka.ui.wisatacandi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.R
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.ItemPreviewBinding

class PreviewAdapter : RecyclerView.Adapter<PreviewAdapter.PreviewViewHolder>() {

    inner class PreviewViewHolder(private val binding: ItemPreviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(/* TODO: Add list for photos */) {
            with(binding) {
                ivPreview.setImageResource(R.drawable.iv_upacara)
            }
        }
    }

    private val diffUtil = object : DiffUtil.ItemCallback<Candi>() {
        override fun areItemsTheSame(oldItem: Candi, newItem: Candi): Boolean {
            return oldItem.namaCandi == newItem.namaCandi
        }

        override fun areContentsTheSame(oldItem: Candi, newItem: Candi): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreviewViewHolder {
        return PreviewViewHolder(
            ItemPreviewBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: PreviewViewHolder, position: Int) {
//        val candi = differ.currentList[position]
        holder.bind()
    }
}