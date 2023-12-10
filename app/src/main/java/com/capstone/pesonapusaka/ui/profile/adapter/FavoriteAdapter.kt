package com.capstone.pesonapusaka.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.ItemFavoritBinding

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemFavoritBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(candi: Candi) {
                with(binding) {
                    tvNamaFavorit.text = candi.namaCandi
                    tvLokasiFavorit.text = candi.lokasiCandi
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoritBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val candi = differ.currentList[position]
        holder.bind(candi)
    }
}