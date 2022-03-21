package com.example.delivery_app.services

import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.models.GetOrdersResponse

object MockApiFakeRetrofitService: IMockApiRetrofitService {

    var mockData: ArrayList<GetOrdersResponse>? = null
    var responseCode: Int = 200

    override fun getOrders(listener: IMockApiRetrofitService.IOnGetOrders) {
        when(responseCode) {
            200 -> {
                if (mockData != null) {
                    listener.onSuccess(mockData!!)
                }
            }
            400 -> {
                listener.onFailed(ErrorResponse("Server is unreachable", 400))
            }
        }
    }
}