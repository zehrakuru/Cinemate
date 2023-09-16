package com.example.cinemate.ui.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cinemate.R
import com.example.cinemate.data.model.DeleteFromCartRequest
import com.example.cinemate.data.model.Product
import com.example.cinemate.databinding.FragmentCartBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), ProductListener {

    private lateinit var binding : FragmentCartBinding
    private val cartAdapter by lazy { CartAdapter(this, ::onDeleteClick) }
    private val viewModel by viewModels<CartViewModel>()
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCart.adapter = cartAdapter
        viewModel.getCartProducts(auth.currentUser?.uid.toString())
        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.cartState.observe(viewLifecycleOwner) {state ->
            when(state) {
                is CartState.Data -> {
                    cartAdapter.submitList(state.products)
                }
                is CartState.Error -> {
                    Snackbar.make(requireView(), state.throwable.message.orEmpty(), 1000).show()
                }
                is CartState.DeleteFromCart -> {
                    Snackbar.make(requireView(), state.baseResponse.message.toString(), 1000).show()
                }
            }
        }
    }

    private fun onDeleteClick(id : Int) {
        val deleteFromCartRequest = DeleteFromCartRequest(id)
        viewModel.deleteFromCart(deleteFromCartRequest)
    }

    override fun onCartItemClick(id: Int) {
        val action = CartFragmentDirections.actionCartToDetail(id)
        findNavController().navigate(action)
    }
}