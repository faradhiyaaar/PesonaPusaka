package com.capstone.pesonapusaka.ui.wisatakuliner.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.capstone.pesonapusaka.data.model.Location
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.databinding.ItemWisataKulinerBinding
import com.capstone.pesonapusaka.ui.maps.MapsActivity
import com.capstone.pesonapusaka.utils.Dimens
import com.capstone.pesonapusaka.utils.glide

class WisataKulinerAdapter: RecyclerView.Adapter<WisataKulinerAdapter.WisataKulinerViewHolder>() {

    inner class WisataKulinerViewHolder(val binding: ItemWisataKulinerBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(umkm: WisataKuliner) {
                with(binding) {
                    tvNamaPenjual.text = umkm.name
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

        val context = holder.itemView.context
        holder.itemView.setOnClickListener {
            if (umkm.latitude != null) {
                context.startActivity(
                    Intent(context, MapsActivity::class.java).also {
                        it.putExtra(Dimens.LOCATION, Location(umkm.latitude, umkm.longitude!!))
                        it.putExtra(Dimens.NAMA_LOKASI, umkm.name)
                    }
                )
            } else {
                Toast.makeText(
                    context,
                    "No location",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.binding.btnFavorite.setOnClickListener {
            onClick?.invoke(umkm)
        }
    }

    var onClick: ((WisataKuliner) -> Unit)? = null
}