package com.example.delivery_app.models

data class GetOrdersResponse(
    val id: Int,
    val description: String,
    val price: Float,
    val deliver_to: String,
    val delivery_address: String,
    val url_image: String,
    val order_status: String
)