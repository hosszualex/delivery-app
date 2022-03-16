package com.example.delivery_app.room

import androidx.annotation.WorkerThread
import com.example.delivery_app.enums.toDeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.RoomDeliveryOrder
import com.example.delivery_app.repositories.IDeliveryOrderRepository

class RoomOrderRepositoryImpl(private val orderDao: IOrderDAO): IDeliveryOrderRepository {

    override suspend fun getDeliveryOrders(listener: IDeliveryOrderRepository.IOnGetDeliveryOrders) {
        listener.onSuccess(getDeliveryOrdersFromRoomDeliveryOrders(orderDao.getOrdersByPrice()))
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    override suspend fun updateDeliveryOrders(
        orders: List<DeliveryOrder>,
        listener: IDeliveryOrderRepository.IOnUpdateDeliveryOrders
    ) {
        orderDao.insert(getRoomDeliveryOrdersFromDeliveryOrders(orders))
        listener.onSuccess()
    }

    private fun getRoomDeliveryOrdersFromDeliveryOrders(data: List<DeliveryOrder>) : List<RoomDeliveryOrder> {
        val deliveryOrders = mutableListOf<RoomDeliveryOrder>()
        data.forEach { order ->
            deliveryOrders.add(
                RoomDeliveryOrder(order.id, order.description, order.price, order.deliverTo, order.deliveryAddress, order.urlImage, order.status.name)
            )
        }
        return deliveryOrders.toList()
    }

    private fun getDeliveryOrdersFromRoomDeliveryOrders(data: List<RoomDeliveryOrder>) : List<DeliveryOrder> {
        val deliveryOrders = mutableListOf<DeliveryOrder>()
        data.forEach { order ->
            deliveryOrders.add(
                DeliveryOrder(order.id, order.description, order.price, order.deliverTo, order.deliveryAddress, order.urlImage, order.status.toDeliveryStatusEnum())
            )
        }
        deliveryOrders.sortByDescending { it.price }
        return deliveryOrders.toList()
    }

}