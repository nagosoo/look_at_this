package com.eunji.lookatthis.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {

    fun convertDateFormat(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())

        val date = inputFormat.parse(dateTimeString)
        return outputFormat.format(date!!)
    }

}