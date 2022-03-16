package com.example.delivery_app.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.delivery_app.R
import com.squareup.picasso.Picasso

@BindingAdapter("android:srcUrl")
fun ImageView.setImageResourceFromUrl(url: String) {
    Picasso.get().load(url).placeholder(R.drawable.placeholder_flower).fit().into(this)
}

