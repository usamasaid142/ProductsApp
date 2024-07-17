package com.osama.productsapp.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osama.productsapp.model.GetProductsRespnse
import com.osama.productsapp.repository.ProductRepository
import com.osama.productsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
@HiltViewModel
class ProductViewModel @Inject constructor(private val repo: ProductRepository):ViewModel() {

    val allProductResponse= MutableLiveData<Resource<GetProductsRespnse>>()


    fun getAllProducts()=viewModelScope.launch(Dispatchers.IO+handler) {
        allProductResponse.postValue(Resource.Loading())
        val response=repo.getAllProducts()
        allProductResponse.postValue(response?.let { handleGetAllProducts(it) })
    }

    private fun handleGetAllProducts(response: Response<GetProductsRespnse>): Resource<GetProductsRespnse>? {
        if (response.isSuccessful){
            response.body()?.let {
                return Resource.sucess(it)
            }
        }
        return Resource.Error(response.message())
    }

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("exception","exception:${exception.message.toString()}")
    }
}