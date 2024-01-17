package com.example.delivery_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.delivery_app.Constants
import com.example.delivery_app.databinding.FragmentOrderListBinding
import com.example.delivery_app.extensions.addFragmentOnTopWithAnimationLeftToRight
import com.example.delivery_app.models.DeliveryOrder
import com.example.delivery_app.models.ErrorResponse
import com.example.delivery_app.ui.activities.MainActivity
import com.example.delivery_app.ui.adapters.OrdersAdapter
import com.example.delivery_app.ui.dialogs.LoadingDialog
import com.example.delivery_app.viewModels.OrderViewModel
import com.example.delivery_app.viewModels.factories.OrderViewModelFactory
import com.google.gson.Gson

class OrderListFragment: Fragment(), OrdersAdapter.IOnOrderClickListener {

    private lateinit var binding: FragmentOrderListBinding
    private val viewModel: OrderViewModel by activityViewModels { OrderViewModelFactory(requireActivity().applicationContext) }
    private lateinit var adapter: OrdersAdapter
    private lateinit var loadingDialog: LoadingDialog

    private val onGetOrders = Observer<List<DeliveryOrder>> { orders ->
        if (!this::adapter.isInitialized) {
            initializeAdapter()
        }
        adapter.setDataSource(orders)
    }

    private fun initializeAdapter() {
        adapter = OrdersAdapter(this)
        binding.rvPosts.adapter = adapter
    }

    private val onUpdateItem = Observer<DeliveryOrder> { deliveryOrder ->
        adapter.updateItemStatus(deliveryOrder.id, deliveryOrder.status)
        (activity as MainActivity).onBackPressed()
    }

    private val isBusy = Observer<Boolean> { isBusy ->
        if (isBusy) {
            loadingDialog.startDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private val onError = Observer<ErrorResponse> { onError ->
        Toast.makeText(requireContext(), "Error Message: " + onError.errorMessage + "\nError Code: " + onError.errorCode, Toast.LENGTH_LONG).show()
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
        binding = FragmentOrderListBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.executePendingBindings()
        loadingDialog = LoadingDialog(requireActivity())
        return binding.root
    }

    private fun connectViewModel() {
        viewModel.isBusy.observe(viewLifecycleOwner, isBusy)
        viewModel.onGetOrders.observe(viewLifecycleOwner, onGetOrders)
        viewModel.onError.observe(viewLifecycleOwner, onError)
        viewModel.onDoneClicked.observe(viewLifecycleOwner, onUpdateItem)
    }

    override fun onOrderClicked(order: DeliveryOrder) {
        val jsonString = Gson().toJson(order)
        activity?.addFragmentOnTopWithAnimationLeftToRight(OrderDetailsFragment().newInstance(jsonString), Constants.ORDER_DETAILS_SCREEN_TAG)
    }

}