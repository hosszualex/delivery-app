package com.example.delivery_app.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.delivery_app.enums.DeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.repositories.IDeliveryOrderRepository
import com.example.delivery_app.repositories.MockApiRepositoryImpl

class OrderViewModel: ViewModel() {

    private val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError

    private val _onGetOrders = MutableLiveData<List<DeliveryOrder>>()
    val onGetOrders: LiveData<List<DeliveryOrder>>
        get() = _onGetOrders

    private val _onDoneClicked = MutableLiveData<Pair<Int, DeliveryStatusEnum>>()
    val onDoneClicked: LiveData<Pair<Int, DeliveryStatusEnum>>
        get() = _onDoneClicked

    private val deliveryOrdersRepository: IDeliveryOrderRepository = MockApiRepositoryImpl()

    private var statusToUpdate: DeliveryStatusEnum = DeliveryStatusEnum.NEW

    fun retrieveOrders() {
        _isBusy.value = true
        deliveryOrdersRepository.getDeliveryOrders(object: IDeliveryOrderRepository.IOnGetDeliveryOrders{
            override fun onSuccess(orders: List<DeliveryOrder>) {
                _onGetOrders.value = orders
                _isBusy.value = false
            }

            override fun onFailed(error: ErrorResponse) {
                _onError.value = error
                _isBusy.value = false
            }
        })
    }

    fun onRefreshClicked() {
        retrieveOrders()
    }

    fun setStatusToUpdate(status: DeliveryStatusEnum) {
        statusToUpdate = status
    }

    fun onDoneClicked(orderId: Int) {
        _onDoneClicked.value = Pair(orderId, statusToUpdate)
    }
}