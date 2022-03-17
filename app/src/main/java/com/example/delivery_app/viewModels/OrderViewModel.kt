package com.example.delivery_app.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.delivery_app.enums.DeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.utils.NetworkUtils
import com.example.delivery_app.repositories.IDeliveryOrderRepository
import com.example.delivery_app.repositories.MockApiRepositoryImpl
import com.example.delivery_app.services.room.OrderRoomDatabase
import com.example.delivery_app.repositories.RoomOrderRepositoryImpl
import kotlinx.coroutines.launch

class OrderViewModel(applicationContext: Context) : ViewModel() {

    private val _isBusy = MutableLiveData<Boolean>()
    val isBusy: LiveData<Boolean>
        get() = _isBusy

    private val _onError = MutableLiveData<ErrorResponse>()
    val onError: LiveData<ErrorResponse>
        get() = _onError

    private val _onGetOrders = MutableLiveData<List<DeliveryOrder>>()
    val onGetOrders: LiveData<List<DeliveryOrder>>
        get() = _onGetOrders

    private val _onDoneClicked = MutableLiveData<DeliveryOrder>()
    val onDoneClicked: LiveData<DeliveryOrder>
        get() = _onDoneClicked

    private val apiRepository: IDeliveryOrderRepository = MockApiRepositoryImpl()
    private val roomRepository: IDeliveryOrderRepository =
        RoomOrderRepositoryImpl(OrderRoomDatabase.getDatabase(applicationContext).orderDao())

    private val isNetworkAvailable
        get() = NetworkUtils.isNetworkConnected

    private val repository: IDeliveryOrderRepository
        get() = if (isNetworkAvailable) {
            apiRepository
        } else {
            roomRepository
        }

    private var statusToUpdate: DeliveryStatusEnum = DeliveryStatusEnum.NEW

    fun retrieveOrders() {
        if (_isBusy.value != null && _isBusy.value == true) {
            return
        }
        _isBusy.value = true
        viewModelScope.launch {
            repository.getDeliveryOrders(object : IDeliveryOrderRepository.IOnGetDeliveryOrders {
                override fun onSuccess(orders: List<DeliveryOrder>) {
                    _onGetOrders.value = orders
                    _isBusy.value = false
                    if (isNetworkAvailable) {
                        updateAllData(orders)
                    }
                }

                override fun onFailed(error: ErrorResponse) {
                    _onError.value = error
                    _isBusy.value = false
                }
            })

        }
    }

    private fun updateData(order: DeliveryOrder) {
        viewModelScope.launch {
            roomRepository.updateDeliveryOrder(order, object: IDeliveryOrderRepository.IOnUpdateDeliveryOrder{
                override fun onSuccess() {}
                override fun onFailed(error: ErrorResponse) { _onError.value = error }
            })
        }
    }

    private fun updateAllData(orders: List<DeliveryOrder>) =
        viewModelScope.launch {
            roomRepository.updateAllDeliveryOrders(orders, object: IDeliveryOrderRepository.IOnUpdateDeliveryOrder{
                override fun onSuccess() {}
                override fun onFailed(error: ErrorResponse) { _onError.value = error }
            })
        }

    fun onRefreshClicked() {
        retrieveOrders()
    }

    fun setStatusToUpdate(status: DeliveryStatusEnum) {
        statusToUpdate = status
    }

    fun onDoneClicked(order: DeliveryOrder) {
        order.status = statusToUpdate
        updateData(order)
        _onDoneClicked.value = order
    }
}