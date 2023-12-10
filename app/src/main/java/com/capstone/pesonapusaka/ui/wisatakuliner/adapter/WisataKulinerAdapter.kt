package com.capstone.pesonapusaka.ui.wisatakuliner.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.UMKM
import com.capstone.pesonapusaka.databinding.ItemWisataKulinerBinding

class WisataKulinerAdapter: RecyclerView.Adapter<WisataKulinerAdapter.WisataKulinerViewHolder>() {

    inner class WisataKulinerViewHolder(private val binding: ItemWisataKulinerBinding):
        RecyclerView.ViewHolder(binding.root) {
            @SuppressLint("SetTextI18n")
            fun bind(umkm: UMKM) {
                with(binding) {
                    tvNamaPenjual.text = umkm.namaPenjualUmkm
                    tvNamaLokasi.text = umkm.namaUmkm
                    tvJarakLokasi.text = "1.2 km"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WisataKulinerViewHolder {
        return WisataKulinerViewHolder(
            ItemWisataKulinerBinding.inflate(
                LayoutInflater.from(parent.context), null, false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: WisataKulinerViewHolder, position: Int) {
        val umkm = differ.currentList[position]
        holder.bind(umkm)
    }
}