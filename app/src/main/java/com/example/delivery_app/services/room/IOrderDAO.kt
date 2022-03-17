package com.example.delivery_app.services.room

import androidx.room.*
import com.example.delivery_app.models.RoomDeliveryOrder

@Dao
interface IOrderDAO {
    @Query("SELECT * FROM order_table ORDER BY price DESC")
    suspend fun getOrdersByPrice(): List<RoomDeliveryOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(order: List<RoomDeliveryOrder>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: RoomDeliveryOrder)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}