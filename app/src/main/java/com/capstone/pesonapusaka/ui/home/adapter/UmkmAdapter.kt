package com.capstone.pesonapusaka.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.databinding.ItemRekomendasiUmkmBinding
import com.capstone.pesonapusaka.utils.glide

class UmkmAdapter: RecyclerView.Adapter<UmkmAdapter.UmkmViewHolder>() {

    inner class UmkmViewHolder(private val binding: ItemRekomendasiUmkmBinding):
        RecyclerView.ViewHolder(binding.root) {
            fun bind(umkm: WisataKuliner) {
                with(binding) {
                    tvNamaPenjual.text = umkm.name
                    tvNamaUmkm.text = umkm.location
                    umkm.thumbnail?.let {
                        ivUmkm.glide(umkm.thumbnail)
                    }
                }
            }
    }

    private val diffUtil = object: DiffUtil.ItemCallback<WisataKuliner>() {
        override fun areItemsTheSame(oldItem: WisataKuliner, newItem: WisataKuliner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: WisataKuliner, newItem: WisataKuliner): Boolean {
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