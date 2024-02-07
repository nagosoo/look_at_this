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

    fun showLoadingDialog(fragmentManager: FragmentManager, context: Context) {
        CommonDialog(
            title = context.getString(R.string.text_loading),
            drawableResId = R.drawable.loading,
        ).show(fragmentManager, CommonDialog.TAG)
    }

    fun showRegisterLinkSuccessDialog(
        fragmentManager: FragmentManager,
        context: Context,
        onPositiveButtonClickListener: () -> Unit
    ) {
        CommonDialog(
            title = context.getString(R.string.text_success_register_link),
            drawableResId = R.drawable.success,
            onPositiveBtnClickListener = onPositiveButtonClickListener
        ).show(fragmentManager, CommonDialog.TAG)
    }

}