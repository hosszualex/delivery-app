package com.example.delivery_app.models

import com.example.delivery_app.enums.DeliveryStatusEnum

data class DeliveryOrder(
    val id: Int,
    val description: String,
    val price: Float,
    val deliverTo: String,
    val deliveryAddress: String,
    val urlImage: String,
    var status: DeliveryStatusEnum = DeliveryStatusEnum.NEW
)
