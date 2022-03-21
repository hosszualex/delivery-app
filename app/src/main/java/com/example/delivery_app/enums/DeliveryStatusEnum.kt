package com.example.delivery_app.enums

enum class DeliveryStatusEnum {
    NEW,
    PENDING,
    DELIVERED
}

fun String.toDeliveryStatusEnum(): DeliveryStatusEnum {
    return when (this) {
        "NEW" -> {
            DeliveryStatusEnum.NEW
        }
        "PENDING" -> {
            DeliveryStatusEnum.PENDING
        }
        "DELIVERED" -> {
            DeliveryStatusEnum.DELIVERED
        }
        else -> {
            DeliveryStatusEnum.NEW
        }
    }
}