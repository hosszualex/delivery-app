package com.example.delivery_app.repositories

import androidx.annotation.WorkerThread
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.RoomDeliveryOrder
import com.example.delivery_app.models.toDeliveryOrder
import com.example.delivery_app.models.toRoomDeliveryOrder
import com.example.delivery_app.services.room.IOrderDAO

class RoomOrderRepositoryImpl(private val orderDao: IOrderDAO): IDeliveryOrderRepository {

    override suspend fun getDeliveryOrders(listener: IDeliveryOrderRepository.IOnGetDeliveryOrders) {
        listener.onSuccess(getDeliveryOrdersFromRoomDeliveryOrders(orderDao.getOrdersByPrice()))
    }

    @WorkerThread
    override suspend fun updateAllDeliveryOrders(
        orders: List<DeliveryOrder>,
        listener: IDeliveryOrderRepository.IOnUpdateDeliveryOrder
    ) {
        orderDao.deleteAll()
        orderDao.insertAll(getRoomDeliveryOrdersFromDeliveryOrders(orders))
        listener.onSuccess()
    }

    override suspend fun updateDeliveryOrder(
        order: DeliveryOrder,
        listener: IDeliveryOrderRepository.IOnUpdateDeliveryOrder
    ) {
        orderDao.insert(order.toRoomDeliveryOrder())
        listener.onSuccess()
    }

    private fun getRoomDeliveryOrdersFromDeliveryOrders(data: List<DeliveryOrder>) : List<RoomDeliveryOrder> {
        val deliveryOrders = mutableListOf<RoomDeliveryOrder>()
        data.forEach { order ->
            deliveryOrders.add(order.toRoomDeliveryOrder())
        }
        return deliveryOrders.toList()
    }

    private fun getDeliveryOrdersFromRoomDeliveryOrders(data: List<RoomDeliveryOrder>) : List<DeliveryOrder> {
        val deliveryOrders = mutableListOf<DeliveryOrder>()
        data.forEach { order ->
            deliveryOrders.add(order.toDeliveryOrder())
        }
        deliveryOrders.sortByDescending { it.price }
        return deliveryOrders.toList()
    }

}