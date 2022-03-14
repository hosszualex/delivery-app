package com.example.delivery_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.delivery_app.databinding.FragmentOrderDetailsBinding
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.viewModels.OrderDetailsViewModel

class OrderDetailsFragment(private val order: DeliveryOrder): Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private lateinit var viewModel: OrderDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(OrderDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = initializeScreen(inflater)
        connectViewModel()
        viewModel.retrieveOrders()
        return rootView
    }

    private fun initializeScreen(inflater: LayoutInflater): View {
        binding = FragmentOrderDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.item = order
        binding.executePendingBindings()
        return binding.root
    }

    private fun connectViewModel() {
//        viewModel.isBusy.observe(viewLifecycleOwner, isBusy)
//        viewModel.onGetOrders.observe(viewLifecycleOwner, onGetOrders)
//        viewModel.onError.observe(viewLifecycleOwner, onError)
    }
}