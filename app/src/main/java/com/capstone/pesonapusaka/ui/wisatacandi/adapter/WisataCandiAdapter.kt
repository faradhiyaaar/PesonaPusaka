package com.capstone.pesonapusaka.ui.wisatacandi.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.ItemWisataCandiBinding
import com.capstone.pesonapusaka.ui.wisatacandi.WisataCandiDetailActivity
import com.capstone.pesonapusaka.utils.Dimens.CANDI

class WisataCandiAdapter: RecyclerView.Adapter<WisataCandiAdapter.WisataCandiViewHolder>() {

    inner class WisataCandiViewHolder(private val binding: ItemWisataCandiBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(candi: Candi) {
                with(binding) {
                    tvLokasiCandi.text = candi.lokasiCandi
                    tvNamaCandi.text = candi.namaCandi
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataCandiViewHolder {
        return WisataCandiViewHolder(
            ItemWisataCandiBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WisataCandiViewHolder, position: Int) {
        val candi = differ.currentList[position]
        holder.bind(candi)

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, WisataCandiDetailActivity::class.java).also {
                    it.putExtra(CANDI, candi)
                }
            )
        }
    }
}