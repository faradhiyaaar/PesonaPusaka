package com.capstone.pesonapusaka.ui.gemerlaptradisi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.databinding.ItemTradisiBinding
import com.capstone.pesonapusaka.ui.gemerlaptradisi.GemerlapTradisiDetailActivity
import com.capstone.pesonapusaka.utils.Dimens.TRADISI
import com.capstone.pesonapusaka.utils.glide

class TradisiAdapter: RecyclerView.Adapter<TradisiAdapter.TradisiViewHolder>() {

    inner class TradisiViewHolder(private val binding: ItemTradisiBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(tradisi: Tradisi) {
                with(binding) {
                    tvNamaTradisi.text = tradisi.namaTradisi
                    tvTanggalTradisi.text = tradisi.tanggalTradisi
                    tradisi.fotoTradisi?.let {
                        ivTradisi.glide(it)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TradisiViewHolder {
        return TradisiViewHolder(
            ItemTradisiBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TradisiViewHolder, position: Int) {
        val tradisi = differ.currentList[position]
        holder.bind(tradisi)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, GemerlapTradisiDetailActivity::class.java).also {
                    it.putExtra(TRADISI, tradisi)
                }
            )
        }
    }
}