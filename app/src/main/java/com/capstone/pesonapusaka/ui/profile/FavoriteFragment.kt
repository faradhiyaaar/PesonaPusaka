package com.capstone.pesonapusaka.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.pesonapusaka.databinding.FragmentFavoriteBinding
import com.capstone.pesonapusaka.ui.profile.adapter.FavoriteAdapter
import com.capstone.pesonapusaka.utils.hide
import com.capstone.pesonapusaka.utils.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteAdapter by lazy { FavoriteAdapter() }
    private val viewModel by viewModels<FavoriteViewModel>()

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

        lifecycleScope.launch {
            val favorites = viewModel.getFavorite().asLiveData()
            favorites.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.tvNoItem.hide()
                }
                binding.tvNoItem.show()
                favoriteAdapter.differ.submitList(it)
            }
        }

        favoriteAdapter.onClick = {
            lifecycleScope.launch {
                viewModel.deleteFavorite(it)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tvNoItem.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}