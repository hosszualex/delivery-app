package com.example.delivery_app.ui.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.example.delivery_app.Constants
import com.example.delivery_app.R
import com.example.delivery_app.databinding.ActivityMainBinding
import com.example.delivery_app.extensions.addFragmentOnTop
import com.example.delivery_app.extensions.lastFragment
import com.example.delivery_app.ui.fragments.OrderListFragment

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this
        this.addFragmentOnTop(OrderListFragment(), Constants.ORDER_LIST_SCREEN_TAG)
    }


    override fun onBackPressed() {
        val lastFragment = this.lastFragment()
        if (lastFragment is OrderListFragment) {
            finish()
        }else {
            super.onBackPressed()
        }
    }
}