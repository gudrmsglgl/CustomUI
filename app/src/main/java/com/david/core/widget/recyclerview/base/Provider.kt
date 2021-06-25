package com.david.core.widget.recyclerview.base

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.RecyclerView.LayoutManager

abstract class Provider {

    lateinit var view: () -> BaseRecyclerView

    internal var visibleUpdate: ((visible: Int) -> Unit)? = null

    internal var scrollPosition: ((position: Int, smooth: Boolean) -> Unit)? = null

    val context: Context
        get() = view().context

    val adapter: BaseRecyclerViewAdapter by lazy {
        onCreateAdapter()
    }

    val layoutManager: LayoutManager?
        get() = view().layoutManager

    val itemDecoration: ItemDecoration? by lazy {
        onCreateItemDecoration()
    }

    protected abstract fun onCreateAdapter(): BaseRecyclerViewAdapter

    protected abstract fun onCreateItemDecoration(): ItemDecoration
}