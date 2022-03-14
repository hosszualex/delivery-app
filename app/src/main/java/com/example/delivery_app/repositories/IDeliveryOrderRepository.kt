package com.example.delivery_app.repositories

import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse

interface IDeliveryOrderRepository {

    fun getDeliveryOrders(listener: IOnGetDeliveryOrders)

    interface IOnGetDeliveryOrders{
        fun onSuccess(orders: List<DeliveryOrder>)
        fun onFailed(error: ErrorResponse)
    }
}