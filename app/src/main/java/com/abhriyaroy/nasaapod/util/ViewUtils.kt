package com.abhriyaroy.nasaapod.util

import android.view.View
import android.view.View.*

fun View.gone() {
    this.visibility = GONE
}

fun View.invisible() {
    this.visibility = INVISIBLE
}

fun View.visible() {
    this.visibility = VISIBLE
}
