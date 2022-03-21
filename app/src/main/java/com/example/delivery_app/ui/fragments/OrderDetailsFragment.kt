package com.example.delivery_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.delivery_app.Constants
import com.example.delivery_app.databinding.FragmentOrderDetailsBinding
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.viewModels.OrderViewModel
import com.example.delivery_app.viewModels.factories.OrderViewModelFactory
import com.google.gson.Gson

class OrderDetailsFragment: Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var order: DeliveryOrder
    private val viewModel: OrderViewModel by activityViewModels { OrderViewModelFactory(requireActivity().applicationContext) }

    fun newInstance(data: String): OrderDetailsFragment {
        val fragment = OrderDetailsFragment()
        val args = Bundle()
        args.putString(Constants.DELIVERY_ORDER_KEY, data)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!this::order.isInitialized){
            val gson = Gson()
            val jsonString = arguments?.getString(Constants.DELIVERY_ORDER_KEY)
            order = if (jsonString == null || jsonString.isEmpty()) {
                DeliveryOrder()
            } else {
                gson.fromJson(jsonString, DeliveryOrder::class.java)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return initializeScreen(inflater)
    }

    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.item = order
        viewModel.setStatusToUpdate(order.status)
        binding.executePendingBindings()
        return binding.root
    }
}