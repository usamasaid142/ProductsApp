package com.osama.productsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.osama.productsapp.adapter.AllProductsAdapter
import com.osama.productsapp.databinding.ProductsfragmentBinding
import com.osama.productsapp.utils.Resource
import com.osama.productsapp.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment() {

  private lateinit var binding:ProductsfragmentBinding
    private val productViewModel: ProductViewModel by viewModels()

    private val productsAdapter :AllProductsAdapter by lazy {
        AllProductsAdapter()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= ProductsfragmentBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         productRecylerview()
        getProductCallBack()
    }

    private fun productRecylerview() {
        binding.rvProducts.apply {
            adapter = productsAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
        }
    }

    private fun getProductCallBack() {
        productViewModel.allProductResponse.observe(
            viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading -> {
                     //   showprogtessbar()
                    }

                    is Resource.sucess -> {
                       // hideprogressbar()
                        response.let {
                            productsAdapter.submitList(it.data?.products)
                            productsAdapter.notifyDataSetChanged()
                        }
                    }

                    is Resource.Error -> {
                      //  hideprogressbar()
                        Snackbar.make(requireView(), "${response.message}", Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            })

        productViewModel.getAllProducts()
    }
}