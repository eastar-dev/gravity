package dev.eastar.gravity

import android.content.res.Resources
import android.util.TypedValue


val Number.dp: Int
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()
val Number.dpf: Float
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), Resources.getSystem().displayMetrics)

val SYSTEM_HEIGHT: Int
    get() = Resources.getSystem().displayMetrics.heightPixels
val SYSTEM_WIDTH: Int
    get() = Resources.getSystem().displayMetrics.widthPixels

val Number.I: Int
    get() = toInt()
val Number.F: Float
    get() = toFloat()
