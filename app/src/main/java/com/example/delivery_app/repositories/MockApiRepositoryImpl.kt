package com.example.delivery_app.repositories

import com.example.delivery_app.enums.toDeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.models.GetOrdersResponse
import com.example.delivery_app.services.IMockApiRetrofitService
import com.example.delivery_app.services.MockApiRetrofitService

class MockApiRepositoryImpl : IDeliveryOrderRepository {
    private val retrofitService: IMockApiRetrofitService

    init {
        retrofitService = MockApiRetrofitService
    }

    override fun getDeliveryOrders(listener: IDeliveryOrderRepository.IOnGetDeliveryOrders) {
        retrofitService.getOrders(object : IMockApiRetrofitService.IOnGetOrders {
            override fun onSuccess(data: ArrayList<GetOrdersResponse>) {
                val deliveryOrders = getDeliveryOrdersFromResponse(data)
                listener.onSuccess(deliveryOrders)
            }

            override fun onFailed(error: ErrorResponse) {
                listener.onFailed(error)
            }
        })
    }

    private fun getDeliveryOrdersFromResponse(data: ArrayList<GetOrdersResponse>): List<DeliveryOrder> {
        val deliveryOrders = mutableListOf<DeliveryOrder>()
        data.forEach { order ->
            deliveryOrders.add(
                DeliveryOrder(order.id, order.description, order.price, order.deliver_to, order.delivery_address, order.url_image, order.order_status.toDeliveryStatusEnum())
            )
        }
        deliveryOrders.sortByDescending { it.price }
        return deliveryOrders.toList()
    }
}