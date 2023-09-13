package com.example.cinemate.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cinemate.MainApplication
import com.example.cinemate.R
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.GetProductDetailResponse
import com.example.cinemate.databinding.FragmentDetailBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding : FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getProductDetail(args.id)
    }

    private fun observeData() = with(binding) {
        viewModel.productDetailLiveData.observe(viewLifecycleOwner) {product ->
            if(product != null) {
            //ivMoviePoster.loadImage(result.imageOne)
            tvMovieTitle.text = product.title
            priceNum.text = "${product.price}"
            tvCategory.text = product.category
            txtDescDetail.text = product.description
            } else {
                Snackbar.make(requireView(), "Empty", 1000).show()
            }
        }

        viewModel.errorMessageLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), it, 1000).show()
        }
    }
}