package com.osama.productsapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osama.productsapp.R
import com.osama.productsapp.databinding.ItemLayoutProductsBinding
import com.osama.productsapp.model.Product
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt


class AllProductsAdapter () :
    ListAdapter<Product, AllProductsAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemLayoutProductsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = getItem(position)

        holder.binding.apply {
            data = product
            val price= product.price?.let { product.discountPercentage?.let { it1 ->
                calculatePrice(it,
                    it1
                )
            } }
            tvTotalPrice.text="${holder.binding.tvTotalPrice.context.getString(
                R.string.egp)} ${price}"
            tvPriceBefore.text="${NumberFormat.getNumberInstance(Locale.US).format(product.price)} ${holder.binding.tvPriceBefore.context.getString(
                R.string.egp)}"
            tvReviews.text="${holder.binding.tvReviews.context.getString(
                R.string.reviews)} (${price})"

        }

    }
    class ViewHolder(itemBinding: ItemLayoutProductsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        internal val binding: ItemLayoutProductsBinding = itemBinding
    }
    private class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return true
        }
    }
    private fun calculatePrice(price:Double,discountPercentage:Double):String{

        val priceBeforeDiscount = price
        val discountPercentage = discountPercentage
        val discountedPrice = priceBeforeDiscount * (1 - discountPercentage / 100)
        val roundedPrice = "%.2f".format(discountedPrice)
        return roundedPrice
    }

}