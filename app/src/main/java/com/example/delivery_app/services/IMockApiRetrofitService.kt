package com.example.delivery_app.services

import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.models.GetOrdersResponse


interface IMockApiRetrofitService {

    fun getOrders(listener: IOnGetOrders)

    interface IOnGetOrders {
        fun onSuccess(data: ArrayList<GetOrdersResponse>)
        fun onFailed(error: ErrorResponse)
    }
}