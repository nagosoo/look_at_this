package com.eunji.lookatthis.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

object DisplayUnitUtil {
    fun dpToPx(dp: Float, context: Context): Int {
        val dm: DisplayMetrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm).toInt()
    }
}