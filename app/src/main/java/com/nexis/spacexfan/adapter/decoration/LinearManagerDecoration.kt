package com.nexis.spacexfan.adapter.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class LinearManagerDecoration(val vSize: Int, val aSize: Int) : RecyclerView.ItemDecoration() {
    private var aPos: Int = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        aPos = parent.getChildAdapterPosition(view)

        if (aPos == (aSize - 1))
            outRect.set(0, vSize, 0, vSize)
        else
            outRect.top = vSize
    }
}