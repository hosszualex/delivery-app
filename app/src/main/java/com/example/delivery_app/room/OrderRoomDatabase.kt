package com.example.delivery_app.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.delivery_app.models.RoomDeliveryOrder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(RoomDeliveryOrder::class), version = 1, exportSchema = false)
abstract class OrderRoomDatabase: RoomDatabase() {
    abstract fun orderDao(): IOrderDAO

    companion object {
        @Volatile
        private var INSTANCE: OrderRoomDatabase? = null

        fun getDatabase(context: Context): OrderRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderRoomDatabase::class.java,
                    "order_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}