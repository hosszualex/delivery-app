package com.example.delivery_app.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.delivery_app.databinding.DeliveryOrderItemBinding
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.utils.OrdersDiffUtil

class OrdersAdapter(private val clickListener: IOnOrderClickListener): RecyclerView.Adapter<OrdersAdapter.ViewHolder>()  {

    private var items: List<DeliveryOrder> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DeliveryOrderItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun setDataSource(newItems: List<DeliveryOrder>) {
        val diffUtil = OrdersDiffUtil(this.items, newItems)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.items = newItems
        diffResults.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: DeliveryOrderItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DeliveryOrder) {
            binding.item = item
            binding.listener = clickListener
            binding.executePendingBindings()
        }
    }

    interface IOnOrderClickListener {
        fun onOrderClicked(order: DeliveryOrder)
    }
}