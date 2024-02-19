package com.eunji.lookatthis.presentation.util

import android.content.ClipboardManager
import android.content.Context


class ClipboardHelper(private val context: Context) {
    private val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    fun getTextFromClipBoard(): String? {
        if (clipboard.hasPrimaryClip()) {
            val item = clipboard.primaryClip!!.getItemAt(0)
            return item.text.toString()
        }
        return null
    }
}
