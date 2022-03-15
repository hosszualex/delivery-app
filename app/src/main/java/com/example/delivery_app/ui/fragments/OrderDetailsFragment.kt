package com.example.delivery_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.delivery_app.databinding.FragmentOrderDetailsBinding
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.viewModels.OrderViewModel

class OrderDetailsFragment(private val order: DeliveryOrder): Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val viewModel: OrderViewModel by activityViewModels()

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
        binding.executePendingBindings()
        return binding.root
    }
}