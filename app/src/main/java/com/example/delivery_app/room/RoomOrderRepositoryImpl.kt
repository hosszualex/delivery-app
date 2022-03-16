package com.example.delivery_app.room

import androidx.annotation.WorkerThread
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.RoomDeliveryOrder
import com.example.delivery_app.repositories.IDeliveryOrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RoomOrderRepositoryImpl(private val orderDao: IOrderDAO): IDeliveryOrderRepository {

    override fun getDeliveryOrders(listener: IDeliveryOrderRepository.IOnGetDeliveryOrders) {
        val orders: Flow<List<DeliveryOrder>> = flow {
            while(true) {
                val orders = orderDao.getOrdersByPrice()
                //listener.onSuccess(orders)
                    //TODO transform
            }
        }
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(order: RoomDeliveryOrder) {
        orderDao.insert(order)
    }
}