package com.example.delivery_app.viewModels.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.delivery_app.viewModels.OrderViewModel

@Suppress("UNCHECKED_CAST")
class OrderViewModelFactory(private val applicationContext: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return OrderViewModel(applicationContext) as T
    }
}