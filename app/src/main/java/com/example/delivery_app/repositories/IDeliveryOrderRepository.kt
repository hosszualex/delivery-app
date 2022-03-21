package com.example.delivery_app.repositories

import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse

interface IDeliveryOrderRepository {

    suspend fun getDeliveryOrders(listener: IOnGetDeliveryOrders)

    suspend fun updateAllDeliveryOrders(orders: List<DeliveryOrder>, listener: IOnUpdateDeliveryOrder)

    suspend fun updateDeliveryOrder(order: DeliveryOrder, listener: IOnUpdateDeliveryOrder)

    interface IOnGetDeliveryOrders{
        fun onSuccess(orders: List<DeliveryOrder>)
        fun onFailed(error: ErrorResponse)
    }

    interface IOnUpdateDeliveryOrder{
        fun onSuccess()
        fun onFailed(error: ErrorResponse)
    }
}