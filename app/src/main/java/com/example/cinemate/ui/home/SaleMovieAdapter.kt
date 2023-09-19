package com.example.cinemate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.Product
import com.example.cinemate.databinding.ItemMovieCardBinding

class SaleMovieAdapter(
    private val productListener: SaleMovieAdapter.ProductListener
) : ListAdapter<Product, SaleMovieAdapter.SaleMovieViewHolder>(ProductDiffCallBack()) {

    class SaleMovieViewHolder(
        private val binding: ItemMovieCardBinding,
        private val productListener: SaleMovieAdapter.ProductListener) : RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false
        fun bind(product: Product) = with(binding) {
            tvMovieName.text = product.title
            tvPrice.text = "$ ${product.price}"
            tvSalePrice.text = "\$ ${product.salePrice}"

            btnFavorite.setOnClickListener {
                isLiked = !isLiked
                btnFavorite.apply {
                    if (isLiked) {
                        playAnimation()
                    } else {
                        cancelAnimation()
                        progress = 0.0f
                    }
                }
            }

            imageViewMovie.loadImage(product.imageOne)

            root.setOnClickListener {
                productListener.onSaleClick(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleMovieViewHolder =
        SaleMovieViewHolder(
            ItemMovieCardBinding.inflate(LayoutInflater.from(parent.context), parent,false), productListener
        )

    override fun onBindViewHolder(holder: SaleMovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    interface ProductListener {
        fun onSaleClick(id:Int)
    }
}