package com.example.cinemate.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide.init
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.DeleteFromCartRequest
import com.example.cinemate.data.model.Product
import com.example.cinemate.databinding.ItemCartBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import okhttp3.internal.notify

class CartAdapter(
    private val productListener: ProductListener,
    var onDeleteClick: (Int) -> Unit = {}
) : ListAdapter<Product, CartAdapter.CartItemViewHolder>(ProductDiffCallBack()) {

    private val productList = mutableListOf<Product>()

    class CartItemViewHolder(
        private val binding: ItemCartBinding,
        private val productListener: ProductListener,
        private var onDeleteClick: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            tvTitle.text = product.title
            ivMoviePoster.loadImage(product.imageOne)
            moviePrice.text = "$ ${product.price}"

            btnDelete.setOnClickListener {
                product.id?.let { it1 -> onDeleteClick(it1) }
            }

            root.setOnClickListener {
                productListener.onCartItemClick(product.id ?: 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false), productListener, onDeleteClick
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun updateList(list: List<Product>) {
        productList.clear()
        productList.addAll(list)
        notifyItemRangeChanged(0, list.size)
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
    fun onCartItemClick(id:Int)
}