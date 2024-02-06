package com.eunji.lookatthis.presentation.util

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.eunji.lookatthis.R
import com.eunji.lookatthis.presentation.view.CommonDialog

object DialogUtil {

    fun closeDialog(fragmentManager: FragmentManager, tag: String) {
        (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
    }

    fun showErrorDialog(fragmentManager: FragmentManager, title: String) {
        CommonDialog(
            title = title,
            drawableResId = R.drawable.error,
        ).show(fragmentManager, CommonDialog.TAG)
    }

    fun showLoadingDialog(fragmentManager: FragmentManager, context: Context) {
        CommonDialog(
            title = context.getString(R.string.text_loading),
            drawableResId = R.drawable.loading,
        ).show(fragmentManager, CommonDialog.TAG)
    }

}