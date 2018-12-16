package com.ovrbach.volvomobility

import android.view.View

interface OnItemClickListener<T> {
    fun onItemClicked(item: T)
}

interface OnItemViewClickedListener {
    fun onItemViewClicked(view: View)
}
