package com.example.delivery_app

import com.example.delivery_app.enums.DeliveryStatusEnum
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.GetOrdersResponse

object FakeConstants {
    val mockResponse = arrayListOf<GetOrdersResponse>(
        GetOrdersResponse(1, "Roses1", 14.4f, "Mihai1", "address 1", "url_image 1", "NEW"),
        GetOrdersResponse(2, "Roses2", 14.5f, "Mihai2", "address 2", "url_image 2", "PENDING"),
        GetOrdersResponse(3, "Roses3", 14.6f, "Mihai3", "address 3", "url_image 3", "NEW")
    )

    val expectedMockResponse = listOf<DeliveryOrder>(
        DeliveryOrder(3, "Roses3", 14.6f, "Mihai3", "address 3", "url_image 3"),
        DeliveryOrder(2, "Roses2", 14.5f, "Mihai2", "address 2", "url_image 2", DeliveryStatusEnum.PENDING),
        DeliveryOrder(1, "Roses1", 14.4f, "Mihai1", "address 1", "url_image 1")
    )
}