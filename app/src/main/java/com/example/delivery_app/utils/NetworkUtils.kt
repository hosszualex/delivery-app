package com.example.delivery_app.utils

import android.content.Context
import android.net.*

object NetworkUtils {
    private var connectivityManager: ConnectivityManager? = null
    val isNetworkConnected: Boolean
        get() = connectivityManager?.activeNetwork != null
    fun initializeUtils(context: Context) {
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}