package com.example.delivery_app

import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.GetOrdersResponse

object FakeConstants {
    val mockResponse = arrayListOf<GetOrdersResponse>(
        GetOrdersResponse(1, "Roses1", 14.4f, "Mihai1"),
        GetOrdersResponse(2, "Roses2", 14.5f, "Mihai2"),
        GetOrdersResponse(3, "Roses3", 14.6f, "Mihai3")
    )

    val expectedMockResponse = listOf<DeliveryOrder>(
        DeliveryOrder(3, "Roses3", 14.6f, "Mihai3"),
        DeliveryOrder(2, "Roses2", 14.5f, "Mihai2"),
        DeliveryOrder(1, "Roses1", 14.4f, "Mihai1")
    )
}