package com.example.cinemate.ui.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.R
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.mapper.mapToProductUI
import com.example.cinemate.data.model.Product
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.databinding.ItemAllMovieBinding

class MovieAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, MovieAdapter.MovieViewHolder>(ProductDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
        ItemAllMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false), productListener
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(
        private val binding: ItemAllMovieBinding,
        private val productListener: ProductListener) : RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false
        fun bind(product: Product) = with(binding) {
            tvMovieTitle.text = product.title
            txtViewPrice.text = "$ ${product.price}"
            tvRate.text = "${product.rate}"
            ivMoviePoster.loadImage(product.imageOne)

            btnFavorite.setOnClickListener {
                productListener.onFavoriteButtonClick(product.mapToProductUI())
                btnFavorite.playAnimation()
            }

            if ((product.saleState != null) && (product.saleState)) {
                tvSalePrice.text = "$ ${product.salePrice}"
                txtViewPrice.setTextColor(Color.parseColor("#FF0000"))
                txtViewPrice.setBackgroundResource(R.drawable.strike_through)
            }

            root.setOnClickListener {
                productListener.onProductClick(product.id ?: 1)
            }
        }
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
        fun onProductClick(id:Int)
        fun onFavoriteButtonClick(product: ProductUI)
    }

}