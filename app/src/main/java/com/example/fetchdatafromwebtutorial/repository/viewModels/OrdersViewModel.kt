package com.example.fetchdatafromwebtutorial.repository.viewModels


import android.os.SystemClock
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchdatafromwebtutorial.AuthActivity
import com.example.fetchdatafromwebtutorial.constants.ApiStatus
import com.example.fetchdatafromwebtutorial.network.ShoesApi
import com.example.fetchdatafromwebtutorial.repository.models.Order
import kotlinx.coroutines.launch


class OrdersViewModel: ViewModel() {

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
            _orders.value = ShoesApi.retrofitService.getOrders("Bearer ${AuthActivity.authToken}")
            _status.value = ApiStatus.DONE
            orders = _orders
        }
    }

    fun updateOrders(){
        viewModelScope.launch {
            SystemClock.sleep(3_000)
            _status.value = ApiStatus.LOADING
            _orders.value = ShoesApi.retrofitService.getOrders("Bearer ${AuthActivity.authToken}")
            _status.value = ApiStatus.DONE
            orders = _orders
        }
    }

}
