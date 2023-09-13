package com.example.cinemate.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.Product
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
        fun bind(product: Product) = with(binding) {
            tvMovieTitle.text = product.title
            txtViewPrice.text = "${product.price}"
            tvRate.text = "${product.rate}"

            ivMoviePoster.loadImage(product.imageOne)

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
    }
}