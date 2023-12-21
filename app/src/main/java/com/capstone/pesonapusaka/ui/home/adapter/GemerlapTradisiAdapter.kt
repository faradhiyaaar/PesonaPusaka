package com.capstone.pesonapusaka.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.databinding.ItemGemerlapTradisiBinding
import com.capstone.pesonapusaka.ui.gemerlaptradisi.GemerlapTradisiDetailActivity
import com.capstone.pesonapusaka.utils.Dimens
import com.capstone.pesonapusaka.utils.glide

class GemerlapTradisiAdapter: RecyclerView.Adapter<GemerlapTradisiAdapter.GemerlapTradisiViewHolder>() {

    inner class GemerlapTradisiViewHolder(private val binding: ItemGemerlapTradisiBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(tradisi: Tradisi) {
                with(binding) {
                    tvNamaUpacara.text = tradisi.namaTradisi
                    tradisi.fotoTradisi?.let {
                        ivUpacara.glide(tradisi.fotoTradisi)
                    }
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<Tradisi>() {
        override fun areItemsTheSame(oldItem: Tradisi, newItem: Tradisi): Boolean {
            return oldItem.namaTradisi == newItem.namaTradisi
        }

        override fun areContentsTheSame(oldItem: Tradisi, newItem: Tradisi): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GemerlapTradisiViewHolder {
        return GemerlapTradisiViewHolder(
            ItemGemerlapTradisiBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GemerlapTradisiViewHolder, position: Int) {
        val tradisi = differ.currentList[position]
        holder.bind(tradisi)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, GemerlapTradisiDetailActivity::class.java).also {
                    it.putExtra(Dimens.TRADISI, tradisi)
                }
            )
        }
    }
}