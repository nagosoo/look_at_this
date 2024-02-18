package com.eunji.lookatthis.presentation.util

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.eunji.lookatthis.R
import com.eunji.lookatthis.presentation.view.CommonDialog

object DialogUtil {

    fun closeDialog(fragmentManager: FragmentManager, tag: String = CommonDialog.TAG) {
        (fragmentManager.findFragmentByTag(tag) as? DialogFragment)?.dismiss()
    }

    fun showErrorDialog(fragmentManager: FragmentManager, title: String) {
        closeDialog(fragmentManager)
        CommonDialog(
            title = title,
            drawableResId = R.drawable.error,
        ).show(fragmentManager, CommonDialog.TAG)
    }

    fun showLoadingDialog(
        fragmentManager: FragmentManager,
        context: Context,
        title: String? = null
    ) {
        CommonDialog(
            title = title ?: context.getString(R.string.text_loading),
            drawableResId = R.drawable.loading,
            showPositiveButton = false
        ).show(fragmentManager, CommonDialog.TAG)
    }

    fun showSuccessDialog(
        fragmentManager: FragmentManager,
        title: String,
        onPositiveButtonClickListener: () -> Unit
    ) {
        CommonDialog(
            title = title,
            drawableResId = R.drawable.success,
            onPositiveBtnClickListener = onPositiveButtonClickListener
        ).show(fragmentManager, CommonDialog.TAG)
    }

}