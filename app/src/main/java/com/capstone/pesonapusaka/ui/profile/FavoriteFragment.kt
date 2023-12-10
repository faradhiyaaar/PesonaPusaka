package com.capstone.pesonapusaka.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.data.model.Candi
import com.capstone.pesonapusaka.databinding.FragmentFavoriteBinding
import com.capstone.pesonapusaka.ui.profile.adapter.FavoriteAdapter

class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteAdapter by lazy { FavoriteAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvFavorit.apply {
            adapter = favoriteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        val listCandiDummy = mutableListOf<Candi>()
        val candi = Candi(namaCandi = "Candi Ratu Boko", lokasiCandi = "Daerah Istimewa Yogyakarta")
        for (i in 0..10) {
            listCandiDummy.add(candi)
        }

        favoriteAdapter.differ.submitList(listCandiDummy)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}