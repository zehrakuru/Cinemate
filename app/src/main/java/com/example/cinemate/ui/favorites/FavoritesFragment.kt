package com.example.cinemate.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.cinemate.R
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.databinding.FragmentFavoritesBinding
import com.example.cinemate.ui.home.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), FavoritesAdapter.FavoriteProductListener {

    private lateinit var binding : FragmentFavoritesBinding
    private val favAdapter by lazy { FavoritesAdapter(this) }
    private val viewModel by viewModels<FavoritesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoriteProducts()
        binding.rvSearch.adapter = favAdapter
        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.favState.observe(viewLifecycleOwner) {state ->
            when(state) {
                is FavState.Data -> {
                    favAdapter.submitList(state.products)
                }
                is FavState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
            }
        }
    }

    override fun onFavButtonClick(product: ProductUI) {
        viewModel.deleteProductFromFavorites(product)
    }

}