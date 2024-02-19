package com.eunji.lookatthis.presentation.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.eunji.lookatthis.presentation.R

object UrlOpenUtil {

    fun openUrl(url: String, context: Context, fragmentManager: FragmentManager) {
        try {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(url))
            ContextCompat.startActivity(context, browserIntent, null)
        } catch (e: Exception) {
            DialogUtil.showErrorDialog(
                fragmentManager,
                context.getString(R.string.text_fail_open_url)
            )
        }
    }

}