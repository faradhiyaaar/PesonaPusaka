package com.capstone.pesonapusaka.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.ItemCandiPopulerBinding

class CandiPopulerAdapter: RecyclerView.Adapter<CandiPopulerAdapter.CandiPopulerViewHolder>() {

    inner class CandiPopulerViewHolder(private val binding: ItemCandiPopulerBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(candi: Candi) {
                with(binding) {
                    tvNamaCandi.text = candi.namaCandi
                    tvLokasiCandi.text = candi.lokasiCandi
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Candi>() {
        override fun areItemsTheSame(oldItem: Candi, newItem: Candi): Boolean {
            return oldItem.namaCandi == newItem.namaCandi
        }

        override fun areContentsTheSame(oldItem: Candi, newItem: Candi): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandiPopulerViewHolder {
        return CandiPopulerViewHolder(
            ItemCandiPopulerBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: CandiPopulerViewHolder, position: Int) {
        val candi = differ.currentList[position]
        holder.bind(candi)
    }
}