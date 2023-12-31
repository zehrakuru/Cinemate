package com.example.cinemate.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.databinding.FragmentSearchBinding
import com.example.cinemate.ui.home.MovieAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment(), MovieAdapter.ProductListener {

    private lateinit var binding : FragmentSearchBinding
    private val movieAdapter by lazy { MovieAdapter(this) }
    private val viewModel by viewModels<SearchViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSearch.adapter = movieAdapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.searchProduct(p0)
                return true
            }
        })

        observeData()
    }

    private fun observeData() {

        viewModel.searchState.observe(viewLifecycleOwner) {state ->

            when(state) {
                is SearchState.Data -> {
                    movieAdapter.submitList(state.products)
                }
                is SearchState.Error -> {
                    Snackbar.make(requireView(), "There is no such a movie!", 1000).show()
                }
            }
        }
    }

    override fun onProductClick(id: Int) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailFragment(id)
        findNavController().navigate(action)
    }

    override fun onFavoriteButtonClick(product: ProductUI) {
        viewModel.addToFavorites(product)
    }
}