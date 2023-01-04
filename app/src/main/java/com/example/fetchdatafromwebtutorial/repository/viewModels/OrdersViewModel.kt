package com.example.fetchdatafromwebtutorial.repository.viewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchdatafromwebtutorial.network.ShoesApi
import com.example.fetchdatafromwebtutorial.repository.models.Order
import kotlinx.coroutines.launch


enum class OrderApiStatus { LOADING, ERROR, DONE }

class OrdersViewModel: ViewModel() {

    var orderList: MutableLiveData<Array<Order>> = MutableLiveData()
    private val _status = MutableLiveData<OrderApiStatus>()
    val status: LiveData<OrderApiStatus> = _status
    private val _orders = MutableLiveData<Array<Order>>()
    var orders: LiveData<Array<Order>> = _orders

    //инициализируем список и заполняем его данными пользователей
    init {
        getOrders()
    }

    fun getListOrders() = orders

    private fun getOrders() {

        viewModelScope.launch {
            _status.value = OrderApiStatus.LOADING
            _orders.value = ShoesApi.retrofitService.getOrders()
            _status.value = OrderApiStatus.DONE
            orders = _orders
        }
    }

    fun updateOrders(){
        viewModelScope.launch {
            _status.value = OrderApiStatus.LOADING
            _orders.value = ShoesApi.retrofitService.getOrders()
            _status.value = OrderApiStatus.DONE
            orders = _orders
        }
    }

}