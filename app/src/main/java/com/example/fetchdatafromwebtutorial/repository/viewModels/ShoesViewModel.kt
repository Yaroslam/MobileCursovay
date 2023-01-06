package com.example.fetchdatafromwebtutorial.repository.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchdatafromwebtutorial.AuthActivity
import com.example.fetchdatafromwebtutorial.constants.ApiStatus
import com.example.fetchdatafromwebtutorial.network.ShoesApi
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import kotlinx.coroutines.launch


class ShoesViewModel: ViewModel() {

    var userList : MutableLiveData<Array<Shoes>> = MutableLiveData()
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus> = _status
    private val _shoes = MutableLiveData<Array<Shoes>>()
    val shoes: LiveData<Array<Shoes>> = _shoes
    //инициализируем список и заполняем его данными пользователей
    init {
        getMarsPhotos()
    }

    fun getListUsers() = shoes

    private fun getMarsPhotos() {

        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            _shoes.value = ShoesApi.retrofitService.getShoes("Bearer ${AuthActivity.authToken}")
            _status.value = ApiStatus.DONE

        }
    }

}