package com.capstone.pesonapusaka.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.data.model.Tradisi
import com.capstone.pesonapusaka.data.model.UMKM
import com.capstone.pesonapusaka.databinding.FragmentHomeBinding
import com.capstone.pesonapusaka.ui.ceritajelajah.CeritaJelajahActivity
import com.capstone.pesonapusaka.ui.gemerlaptradisi.GemerlapTradisiActivity
import com.capstone.pesonapusaka.ui.home.adapter.CandiPopulerAdapter
import com.capstone.pesonapusaka.ui.home.adapter.GemerlapTradisiAdapter
import com.capstone.pesonapusaka.ui.home.adapter.UmkmAdapter
import com.capstone.pesonapusaka.ui.santunberkunjung.SantunBerkunjungActivity
import com.capstone.pesonapusaka.ui.wisatacandi.WisataCandiActivity
import com.capstone.pesonapusaka.ui.wisatakuliner.WisataKulinerActivity

class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val candiPopulerAdapter by lazy { CandiPopulerAdapter() }
    private val rekomendasiUmkmAdapter by lazy { UmkmAdapter() }
    private val gemerlapTradisiAdapter by lazy { GemerlapTradisiAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCandiPopuler()
        setupRekomendasiUmkm()
        setupGemerlapTradisi()
        setActions()

    }

    private fun setActions() {
        binding.cvSantunBerkunjung.setOnClickListener {
            startActivity(
                Intent(requireActivity(), SantunBerkunjungActivity::class.java)
            )
        }
        binding.btnGemerlapTradisi.setOnClickListener {
            startActivity(
                Intent(requireActivity(), GemerlapTradisiActivity::class.java)
            )
        }
        binding.cvWisataCandi.setOnClickListener {
            startActivity(
                Intent(requireActivity(), WisataCandiActivity::class.java)
            )
        }
        binding.cvWisataKuliner.setOnClickListener {
            startActivity(
                Intent(requireActivity(), WisataKulinerActivity::class.java)
            )
        }
        binding.tvLihatCandi.setOnClickListener {
            startActivity(
                Intent(requireActivity(), WisataCandiActivity::class.java)
            )
        }
        binding.tvLihatKuliner.setOnClickListener {
            startActivity(
                Intent(requireActivity(), WisataKulinerActivity::class.java)
            )
        }
        binding.cvCeritaJelajah.setOnClickListener {
            startActivity(
                Intent(requireActivity(), CeritaJelajahActivity::class.java)
            )
        }
    }

    private fun setupCandiPopuler() {
        binding.rvCandiPopuler.apply {
            adapter = candiPopulerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val listCandiDummy = mutableListOf<Candi>()
        val candi = Candi(namaCandi = "Candi Ratu Boko", lokasiCandi = "Daerah Istimewa Yogyakarta")
        for (i in 0..10) {
            listCandiDummy.add(candi)
        }

        candiPopulerAdapter.differ.submitList(listCandiDummy)
    }

    private fun setupRekomendasiUmkm() {
        binding.rvRekomendasiUmkm.apply {
            adapter = rekomendasiUmkmAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val listUmkmDummy = mutableListOf<UMKM>()
        val umkm = UMKM(namaUmkm = "Warung Soto Sawah", namaPenjualUmkm = "Pak Par")
        for (i in 0..10) {
            listUmkmDummy.add(umkm)
        }

        rekomendasiUmkmAdapter.differ.submitList(listUmkmDummy)
    }

    private fun setupGemerlapTradisi() {
        binding.rvGemerlapTradisi.apply {
            adapter = gemerlapTradisiAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        val listTradisi = mutableListOf<Tradisi>()
        val tradisi = Tradisi(namaTradisi = "Upacara Angayuba")
        for (i in 0..10) {
            listTradisi.add(tradisi)
        }

        gemerlapTradisiAdapter.differ.submitList(listTradisi)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}