package com.example.delivery_app.models

import com.example.delivery_app.enums.DeliveryStatusEnum

data class DeliveryOrder(
    val id: Int = 0,
    val description: String = "",
    val price: Float = 0f,
    val deliverTo: String = "",
    val deliveryAddress: String = "",
    val urlImage: String = "",
    var status: DeliveryStatusEnum = DeliveryStatusEnum.NEW
)

fun DeliveryOrder.toRoomDeliveryOrder(): RoomDeliveryOrder {
    return RoomDeliveryOrder(id, description, price, deliverTo, deliveryAddress, urlImage, status.name)
}
