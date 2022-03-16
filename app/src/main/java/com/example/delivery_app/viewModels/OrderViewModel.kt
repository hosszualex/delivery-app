package com.example.delivery_app.viewModels

import android.content.Context
import androidx.lifecycle.*
import com.example.delivery_app.enums.DeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.repositories.IDeliveryOrderRepository
import com.example.delivery_app.repositories.MockApiRepositoryImpl
import com.example.delivery_app.room.OrderRoomDatabase
import com.example.delivery_app.room.RoomOrderRepositoryImpl
import kotlinx.coroutines.launch

class OrderViewModel(applicationContext: Context) : ViewModel() {

    private var statusToUpdate: DeliveryStatusEnum = DeliveryStatusEnum.NEW

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

    private val apiRepository: IDeliveryOrderRepository = MockApiRepositoryImpl()
    private val roomRepository: IDeliveryOrderRepository =
        RoomOrderRepositoryImpl(OrderRoomDatabase.getDatabase(applicationContext).orderDao())

    private val repository: IDeliveryOrderRepository
        get() = if (isNetworkAvailable) {
            apiRepository
        } else {
            roomRepository
        }

    //TODO make this work
    private val isNetworkAvailable = false

    fun retrieveOrders() {
        _isBusy.value = true
        viewModelScope.launch {
            repository.getDeliveryOrders(object : IDeliveryOrderRepository.IOnGetDeliveryOrders {
                override fun onSuccess(orders: List<DeliveryOrder>) {
                    _onGetOrders.value = orders
                    _isBusy.value = false
                    if (isNetworkAvailable) {
                        updateData(orders)
                    }
                }

                override fun onFailed(error: ErrorResponse) {
                    _onError.value = error
                    _isBusy.value = false
                }
            })
        }
    }

    private fun updateData(orders: List<DeliveryOrder>) =
        viewModelScope.launch {
            roomRepository.updateDeliveryOrders(orders, object: IDeliveryOrderRepository.IOnUpdateDeliveryOrders{
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

    fun onDoneClicked(orderId: Int) {
        _onDoneClicked.value = Pair(orderId, statusToUpdate)
    }
}

class OrderViewModelFactory(private val mApplication: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(mApplication) as T
    }
}