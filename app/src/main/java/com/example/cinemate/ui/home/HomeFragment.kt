package com.example.cinemate.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemate.common.gone
import com.example.cinemate.common.visible
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), MovieAdapter.ProductListener, SaleMovieAdapter.ProductListener {

    private lateinit var binding : FragmentHomeBinding
    private val movieAdapter by lazy {MovieAdapter(this)}
    private val saleMovieAdapter by lazy { SaleMovieAdapter(this) }
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSecond.adapter = movieAdapter
        viewModel.getProducts()

        binding.rvFirst.adapter = saleMovieAdapter
        viewModel.getSaleProducts()
        observeData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onProductClick(id: Int) {
        val action = HomeFragmentDirections.actionHomeToDetail(id)
        findNavController().navigate(action)
    }

    override fun onFavoriteButtonClick(product: ProductUI) {
        viewModel.addToFavorites(product)
    }

    private fun observeData() {
        viewModel.homeState.observe(viewLifecycleOwner) { state ->
            when(state) {
                is HomeState.Data -> {
                    movieAdapter.submitList(state.products)
                    binding.progressBar.gone()
                }
                is HomeState.SaleData -> {
                    saleMovieAdapter.submitList(state.products)
                    binding.progressBar.gone()
                }
                is HomeState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                    binding.progressBar.gone()
                }
                is HomeState.Loading -> {
                    binding.progressBar.visible()
                }
            }
        }
    }

    override fun onSaleClick(id: Int) {
        val action = HomeFragmentDirections.actionHomeToDetail(id)
        findNavController().navigate(action)
    }
}