package com.example.delivery_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse

abstract class BaseViewModel: ViewModel() {
    protected val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy
    protected val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError
    protected val _onGetOrders = MutableLiveData<List<DeliveryOrder>>()
    val onGetOrders: LiveData<List<DeliveryOrder>>
        get() = _onGetOrders
}