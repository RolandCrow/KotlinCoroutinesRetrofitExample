package com.example.kotlincoroutinesretrofitexample.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) { // работа с изображением во вью
    url?.let {
        Glide.with(context)
            .load(it)
            .into(this)
    }
}