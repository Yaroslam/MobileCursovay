package com.example.fetchdatafromwebtutorial.repository.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fetchdatafromwebtutorial.network.ShoesApi
import com.example.fetchdatafromwebtutorial.repository.models.Shoes
import kotlinx.coroutines.launch


enum class ShoesApiStatus { LOADING, ERROR, DONE }

class ShoesViewModel: ViewModel() {

    var userList : MutableLiveData<Array<Shoes>> = MutableLiveData()
    private val _status = MutableLiveData<ShoesApiStatus>()
    val status: LiveData<ShoesApiStatus> = _status
    private val _shoes = MutableLiveData<Array<Shoes>>()
    val shoes: LiveData<Array<Shoes>> = _shoes
    //инициализируем список и заполняем его данными пользователей
    init {
        getMarsPhotos()
    }

    fun getListUsers() = shoes

    private fun getMarsPhotos() {

        viewModelScope.launch {
            _status.value = ShoesApiStatus.LOADING
            _shoes.value = ShoesApi.retrofitService.getPhotos()
            _status.value = ShoesApiStatus.DONE

        }
    }

}