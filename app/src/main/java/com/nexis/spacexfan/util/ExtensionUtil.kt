package com.nexis.spacexfan.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.nexis.spacexfan.R

fun String.show(v: View, msg: String){
    Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show()
}

fun ImageView.downloadImageUrl(imageUrl: String?){
    val options = RequestOptions()
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(imageUrl)
        .into(this)
}


@BindingAdapter("android:downloadImg")
fun downloadImage(view: ImageView, url: String?){
    view.downloadImageUrl(url)
}