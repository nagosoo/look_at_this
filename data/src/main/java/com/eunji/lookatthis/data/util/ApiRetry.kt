package com.eunji.lookatthis.data.util

import android.util.Log

object ApiRetry {

     suspend fun <T> retry(numberOfRetries: Int, block: suspend () -> T): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (exception: Exception) {
                Log.d("logging", "error ${exception.stackTrace}")
            }
        }
        return block()
    }

}