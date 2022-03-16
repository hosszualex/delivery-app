package com.example.delivery_app.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.delivery_app.models.RoomDeliveryOrder

@Dao
interface IOrderDAO {
    @Query("SELECT * FROM order_table ORDER BY price DESC")
    fun getOrdersByPrice(): List<RoomDeliveryOrder>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: RoomDeliveryOrder)

    @Query("DELETE FROM order_table")
    suspend fun deleteAll()
}