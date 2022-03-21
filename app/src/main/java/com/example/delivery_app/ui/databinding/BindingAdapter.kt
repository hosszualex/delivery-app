package com.example.delivery_app.ui.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.delivery_app.R
import com.squareup.picasso.Picasso

@BindingAdapter("android:srcUrl")
fun ImageView.setImageResourceFromUrl(url: String) {
    if (url.isEmpty()) {
        Picasso.get().load(R.drawable.placeholder_flower).placeholder(R.drawable.placeholder_flower).fit().into(this)
    } else {
        Picasso.get().load(url).placeholder(R.drawable.placeholder_flower).fit().into(this)
    }
}

