package com.artyomefimov.mystorage.view.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun loadImageFrom(context: Context, uri: Uri, imageView: ImageView) =
    Glide.with(context)
        .load(uri)
        .apply(RequestOptions.circleCropTransform())
        .into(imageView)