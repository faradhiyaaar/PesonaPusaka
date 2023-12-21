package com.capstone.pesonapusaka.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Favorite
import com.capstone.pesonapusaka.databinding.ItemFavoritBinding
import com.capstone.pesonapusaka.utils.glide

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ItemFavoritBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(fav: Favorite) {
                with(binding) {
                    tvNamaFavorit.text = fav.name
                    tvLokasiFavorit.text = fav.location
                    ivUmkm.glide(fav.thumbnail)
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
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
        val favorite = differ.currentList[position]
        holder.bind(favorite)

        holder.binding.btnFavorite.setOnClickListener {
            onClick?.invoke(favorite)
        }
    }

    var onClick: ((Favorite) -> Unit)? = null
}