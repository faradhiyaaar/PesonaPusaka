package com.capstone.pesonapusaka.ui.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.CandiModel
import com.capstone.pesonapusaka.data.model.WisataKuliner
import com.capstone.pesonapusaka.databinding.FragmentHomeBinding
import com.capstone.pesonapusaka.ui.ceritajelajah.CeritaJelajahActivity
import com.capstone.pesonapusaka.ui.gemerlaptradisi.GemerlapTradisiActivity
import com.capstone.pesonapusaka.ui.home.adapter.CandiPopulerAdapter
import com.capstone.pesonapusaka.ui.home.adapter.GemerlapTradisiAdapter
import com.capstone.pesonapusaka.ui.home.adapter.UmkmAdapter
import com.capstone.pesonapusaka.ui.santunberkunjung.SantunBerkunjungActivity
import com.capstone.pesonapusaka.ui.wisatacandi.WisataCandiActivity
import com.capstone.pesonapusaka.ui.wisatakuliner.WisataKulinerActivity
import com.capstone.pesonapusaka.utils.Result
import com.capstone.pesonapusaka.utils.glide
import com.capstone.pesonapusaka.utils.listTradisi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment: Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val candiPopulerAdapter by lazy { CandiPopulerAdapter() }
    private val rekomendasiUmkmAdapter by lazy { UmkmAdapter() }
    private val gemerlapTradisiAdapter by lazy { GemerlapTradisiAdapter() }
    private val viewModel by viewModels<HomeViewModel>()

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

        setupGemerlapTradisi()
        setActions()
        observer()
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

    private fun setupCandiPopuler(data: List<CandiModel>?) {
        binding.rvCandiPopuler.apply {
            adapter = candiPopulerAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        data?.let {
            candiPopulerAdapter.differ.submitList(it)
        }
    }

    private fun setupRekomendasiUmkm(listWisataKuliner: List<WisataKuliner>) {
        binding.rvRekomendasiUmkm.apply {
            adapter = rekomendasiUmkmAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        rekomendasiUmkmAdapter.differ.submitList(listWisataKuliner)
    }

    private fun setupGemerlapTradisi() {
        binding.rvGemerlapTradisi.apply {
            adapter = gemerlapTradisiAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }

        gemerlapTradisiAdapter.differ.submitList(listTradisi)
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        val user = viewModel.user.asLiveData()
        user.observe(viewLifecycleOwner) {
            it.avatar?.let { avatar ->
                binding.ivProfil.glide(avatar)
                binding.tvHelloUsername.text = "Hello, ${it.name}"
            }
        }

        val candi = viewModel.candiRecommendation.asLiveData()
        candi.observe(viewLifecycleOwner) {
            when(it) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> { setupCandiPopuler(it.data) }
                is Result.Void -> {}
            }
        }

        val umkm = viewModel.wisataKuliner.asLiveData()
        umkm.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Error -> {}
                is Result.Loading -> {}
                is Result.Success -> {
                    result.data?.let {
                        setupRekomendasiUmkm(it)
                    }
                }
                is Result.Void -> {}
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}