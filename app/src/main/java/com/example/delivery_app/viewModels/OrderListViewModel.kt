package com.example.delivery_app.viewModels

import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.repositories.IDeliveryOrderRepository
import com.example.delivery_app.repositories.MockApiRepositoryImpl

class OrderListViewModel: BaseViewModel() {
    private val deliveryOrdersRepository: IDeliveryOrderRepository = MockApiRepositoryImpl()

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
}