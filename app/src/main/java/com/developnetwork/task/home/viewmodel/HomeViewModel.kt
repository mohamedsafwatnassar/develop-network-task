package com.developnetwork.task.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.developnetwork.task.common.utils.DataState
import com.developnetwork.task.home.model.ProductItem
import com.developnetwork.task.network.ApiManager
import com.developnetwork.task.network.WebServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val webServices = ApiManager().getClient()?.create(WebServices::class.java)

    private val _getAllProductsState: MutableLiveData<DataState<ArrayList<ProductItem>?>> =
        MutableLiveData()
    val getAllProductsLive: LiveData<DataState<ArrayList<ProductItem>?>>
        get() = _getAllProductsState

    fun getAllProducts() {
        _getAllProductsState.postValue(DataState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = webServices!!.getAllProducts()
                if (response.isSuccessful) {
                    _getAllProductsState.postValue(DataState.Success(response.body()!!.products))
                    Log.d("isSuccessful", "${response.body()!!.products}")
                } else {
                    _getAllProductsState.postValue(DataState.ErrorMessage(response.message()))
                    _getAllProductsState.postValue(DataState.Finished)
                    Log.d("else", response.message())
                }
            } catch (error: Exception) {
                _getAllProductsState.postValue(DataState.ErrorMessage(error.localizedMessage))
                _getAllProductsState.postValue(DataState.Finished)
                Log.d("catch", error.localizedMessage)
            }
        }
    }
}