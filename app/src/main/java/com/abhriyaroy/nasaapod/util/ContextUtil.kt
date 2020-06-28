package com.abhriyaroy.nasaapod.util

import android.content.Context
import androidx.annotation.DrawableRes

fun Context.drawableRes(@DrawableRes id: Int) = resources.getDrawable(id)!!
