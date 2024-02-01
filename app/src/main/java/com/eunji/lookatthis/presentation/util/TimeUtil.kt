package com.eunji.lookatthis.presentation.util

object TimeUtil {
    fun getHour(time: String): Int {
        val hour = time.split(":")[0].toInt()
        return get12HourFrom24Hour(hour)
    }

    fun getMinute(time: String): Int {
        return time.split(":")[1].toInt()
    }

    fun getAmPm(time: String): String {
        val hour = time.split(":")[0].toInt()
        return if (hour > 12) "PM" else "AM"
    }

    private fun get12HourFrom24Hour(hour: Int): Int {
        return if (hour > 12) hour - 12 else hour
    }

}