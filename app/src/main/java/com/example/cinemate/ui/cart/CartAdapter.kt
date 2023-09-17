package com.example.cinemate.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cinemate.common.loadImage
import com.example.cinemate.data.model.Product
import com.example.cinemate.databinding.ItemCartBinding

class CartAdapter(
    private val productListener: ProductListener
) : ListAdapter<Product, CartAdapter.CartItemViewHolder>(ProductDiffCallBack()) {

    class CartItemViewHolder(
        private val binding: ItemCartBinding,
        private val productListener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) = with(binding) {
            var productCount = 1

            tvTitle.text = product.title
            ivMoviePoster.loadImage(product.imageOne)
            moviePrice.text = "$ ${product.price}"
            productAmountBasket.text = productCount.toString()

            btnDelete.setOnClickListener {
                productListener.onDeleteItemClick(product.id ?: 1)
            }

            root.setOnClickListener {
                productListener.onCartItemClick(product.id ?: 1)
            }

            btnPlusFood.setOnClickListener {
                productListener.onIncreaseClick(product.price)
                productCount++
                productAmountBasket.text = productCount.toString()
            }

            btnMinusFood.setOnClickListener {
                if(productCount != 1){
                    productListener.onDecreaseClick(product.price)
                    productCount--
                    productAmountBasket.text = productCount.toString()
                } else {
                    productListener.onDeleteItemClick(product.id ?: 1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        return CartItemViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false), productListener
        )
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
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
        fun onCartItemClick(id:Int)
        fun onDeleteItemClick(id:Int)
        fun onIncreaseClick(price:Double?)
        fun onDecreaseClick(price:Double?)
    }
}

