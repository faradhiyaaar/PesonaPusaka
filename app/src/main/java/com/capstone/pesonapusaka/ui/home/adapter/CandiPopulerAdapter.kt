package com.capstone.pesonapusaka.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.databinding.ItemCandiPopulerBinding
import com.capstone.pesonapusaka.ui.wisatacandi.WisataCandiDetailActivity
import com.capstone.pesonapusaka.utils.Dimens
import com.capstone.pesonapusaka.utils.glide

class CandiPopulerAdapter: RecyclerView.Adapter<CandiPopulerAdapter.CandiPopulerViewHolder>() {

    inner class CandiPopulerViewHolder(private val binding: ItemCandiPopulerBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(candi: CandiModel) {
                with(binding) {
                    tvNamaCandi.text = candi.name
                    tvLokasiCandi.text = candi.location
                    candi.thumbnail?.let {
                        ivCandi.glide(candi.thumbnail)
                    }
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<CandiModel>() {
        override fun areItemsTheSame(oldItem: CandiModel, newItem: CandiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CandiModel, newItem: CandiModel): Boolean {
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

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, WisataCandiDetailActivity::class.java).also {
                    it.putExtra(Dimens.CANDI, candi)
                }
            )
        }
    }
}