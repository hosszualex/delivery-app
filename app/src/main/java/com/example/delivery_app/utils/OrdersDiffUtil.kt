package com.example.delivery_app.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.delivery_app.models.DeliveryOrder

class OrdersDiffUtil
    (
    private val oldList: List<DeliveryOrder>,
    private val newList: List<DeliveryOrder>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.count()
    }

    override fun getNewListSize(): Int {
        return newList.count()
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when {
            oldList[oldItemPosition].id != newList[newItemPosition].id -> {
                false
            }
            oldList[oldItemPosition].description != newList[newItemPosition].description -> {
                false
            }
            oldList[oldItemPosition].deliverTo != newList[newItemPosition].deliverTo -> {
                false
            }
            oldList[oldItemPosition].price != newList[newItemPosition].price -> {
                false
            }
            oldList[oldItemPosition].deliveryAddress != newList[newItemPosition].deliveryAddress -> {
                false
            }
            oldList[oldItemPosition].urlImage != newList[newItemPosition].urlImage -> {
                false
            }
            else -> {
                true
            }
        }
    }

}