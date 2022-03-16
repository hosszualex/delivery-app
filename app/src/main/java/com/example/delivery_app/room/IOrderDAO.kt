package com.example.delivery_app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.delivery_app.models.RoomDeliveryOrder

@Dao
interface IOrderDAO {
    @Query("SELECT * FROM order_table ORDER BY price DESC")
    suspend fun getOrdersByPrice(): List<RoomDeliveryOrder>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(order: List<RoomDeliveryOrder>)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}