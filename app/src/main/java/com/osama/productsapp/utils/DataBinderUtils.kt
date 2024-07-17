package com.osama.productsapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.osama.productsapp.R

object DataBinderUtils {
    @JvmStatic
    @BindingAdapter("imageApplyString")
    fun imageApplyString(imageView: ImageView, url: String?) {
        Glide.with(imageView.context)
            .asDrawable()
            .placeholder(R.drawable.ic_launcher_background)
            .load(url)
            .into(imageView)
    }
}