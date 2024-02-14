package com.eunji.lookatthis.presentation.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class LinkRecyclerViewItemDecoration(private val bottom: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition < state.itemCount - 1) {
            outRect.bottom = bottom
        } else {
            outRect.bottom = 0
        }
        outRect.top = 0
        outRect.left = 0
        outRect.right = 0
    }
}