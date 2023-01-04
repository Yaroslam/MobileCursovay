package com.example.fetchdatafromwebtutorial.repository.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchdatafromwebtutorial.constants.ApiStatus
import com.example.fetchdatafromwebtutorial.network.ShoesApi
import com.example.fetchdatafromwebtutorial.repository.models.Order
import kotlinx.coroutines.launch

class ExecutorOrdersViewModel: ViewModel() {

    var orderList: MutableLiveData<Array<Order>> = MutableLiveData()
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    private val _orders = MutableLiveData<Array<Order>>()
    var orders: LiveData<Array<Order>> = _orders


    init {
        getOrders()
    }

    fun getListOrders() = orders

    private fun getOrders() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _orders.value = ShoesApi.retrofitService.getExecuteOrders("1")
            _status.value = ApiStatus.DONE
            orders = _orders
        }
    }

    fun updateOrders(){
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _orders.value = ShoesApi.retrofitService.getExecuteOrders("1")
            _status.value = ApiStatus.DONE
            orders = _orders
        }
    }

}