package com.example.delivery_app.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.delivery_app.enums.DeliveryStatusEnum

@Entity(tableName = "order_table")
data class RoomDeliveryOrder(
    @PrimaryKey @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: Float,
    @ColumnInfo(name = "deliverTo") val deliverTo: String,
    @ColumnInfo(name = "deliveryAddress") val deliveryAddress: String,
    @ColumnInfo(name = "urlImage") val urlImage: String,
    @ColumnInfo(name = "status") var status: String
)
