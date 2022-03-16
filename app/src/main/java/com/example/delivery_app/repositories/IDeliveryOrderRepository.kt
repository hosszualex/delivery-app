package com.example.delivery_app.repositories

import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse

interface IDeliveryOrderRepository {

    suspend fun getDeliveryOrders(listener: IOnGetDeliveryOrders)

    suspend fun updateDeliveryOrders(orders: List<DeliveryOrder>, listener: IOnUpdateDeliveryOrders)

    interface IOnGetDeliveryOrders{
        fun onSuccess(orders: List<DeliveryOrder>)
        fun onFailed(error: ErrorResponse)
    }

    interface IOnUpdateDeliveryOrders{
        fun onSuccess()
        fun onFailed(error: ErrorResponse)
    }
}