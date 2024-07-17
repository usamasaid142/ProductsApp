package com.osama.productsapp.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.osama.productsapp.databinding.ItemLayoutProductsBinding
import com.osama.productsapp.model.Product


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
    interface IShowDetails {
        fun onItemClick(id: String)
    }

}