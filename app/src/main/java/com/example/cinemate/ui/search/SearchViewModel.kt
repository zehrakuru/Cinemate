package com.example.cinemate.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinemate.common.Resource
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.repository.ProductsRepository
import com.example.cinemate.ui.detail.DetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productsRepository: ProductsRepository) : ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState>
    get() = _searchState

    fun searchProduct(query:String?) {
        viewModelScope.launch {
            when(val result = productsRepository.searchProduct(query)) {
                is Resource.Success -> {
                    _searchState.value = SearchState.Data(result.data)
                }
                is Resource.Error -> {
                    _searchState.value = SearchState.Error(result.throwable)
                }
            }
        }
    }
}

sealed interface SearchState {
    data class Data(val products: List<Product?>) : SearchState
    data class Error(val throwable: Throwable) : SearchState
}