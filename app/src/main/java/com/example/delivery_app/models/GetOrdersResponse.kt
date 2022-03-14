package com.example.delivery_app.models

data class GetOrdersResponse(
    val id: Int,
    val description: String,
    val price: Float,
    val deliver_to: String
)
//TODO Add timestamp?
//TODO Add Address