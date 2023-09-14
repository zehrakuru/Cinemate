package com.example.cinemate.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

fun ImageView.loadImage(url: String?) {
    Glide.with(this.context).load(url).into(this)
}

fun View.gone(){
    visibility=View.GONE
}
fun View.visible(){
    visibility=View.VISIBLE
}
