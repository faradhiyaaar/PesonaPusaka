package com.capstone.pesonapusaka.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.UMKM
import com.capstone.pesonapusaka.databinding.ItemRekomendasiUmkmBinding

class UmkmAdapter: RecyclerView.Adapter<UmkmAdapter.UmkmViewHolder>() {

    inner class UmkmViewHolder(private val binding: ItemRekomendasiUmkmBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(umkm: UMKM) {
                with(binding) {
                    tvNamaUmkm.text = umkm.namaUmkm
                    tvNamaPenjual.text = umkm.namaPenjualUmkm
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<UMKM>() {
        override fun areItemsTheSame(oldItem: UMKM, newItem: UMKM): Boolean {
            return oldItem.namaUmkm == newItem.namaUmkm
        }

        override fun areContentsTheSame(oldItem: UMKM, newItem: UMKM): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UmkmViewHolder {
        return UmkmViewHolder(
            ItemRekomendasiUmkmBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: UmkmViewHolder, position: Int) {
        val umkm = differ.currentList[position]
        holder.bind(umkm)
    }
}