package com.example.cinemate.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.ProductUI
import com.example.cinemate.databinding.ItemAllMovieBinding

class FavoritesAdapter(
    private val favoriteProductListener: FavoriteProductListener
) : ListAdapter<ProductUI, FavoritesAdapter.FavoriteProductViewHolder>(ProductDiffCallBack()) {

    class FavoriteProductViewHolder(
        private val binding: ItemAllMovieBinding,
        private val favoriteProductListener: FavoriteProductListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductUI) = with(binding) {
            tvMovieTitle.text = product.title
            txtViewPrice.text = "$ ${product.price}"
            tvRate.text = "${product.rate}"
            ivMoviePoster.loadImage(product.imageOne)
            btnFavorite.playAnimation()

            btnFavorite.setOnClickListener {
                favoriteProductListener.onFavButtonClick(product)
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }
    }

    interface FavoriteProductListener {
        fun onFavButtonClick(product: ProductUI)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteProductViewHolder {
        return FavoriteProductViewHolder(
            ItemAllMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false), favoriteProductListener
        )
    }

    override fun onBindViewHolder(
        holder: FavoriteProductViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }
}