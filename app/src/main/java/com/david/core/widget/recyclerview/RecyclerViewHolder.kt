package com.david.core.widget.recyclerview

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewHolder<Item>(private val itemView: View)
    : RecyclerView.ViewHolder(itemView) {

    var item: Item? = null
        private set
    var isItemChange = false
        private set
    var isAutoImageRelease = true
    lateinit var mArguments: Bundle
    private var mFirstLoad = true


    constructor(
        viewGroup: ViewGroup,
        @LayoutRes itemVieId: Int
    ) : this(LayoutInflater.from(viewGroup.context).inflate(itemVieId, viewGroup, false))

    constructor(
        viewGroup: SwipeRefreshRecyclerView,
        @LayoutRes itemVieId: Int
    ) : this(buildItemView(viewGroup.getRecyclerView(), itemVieId))

    abstract fun onRefresh(item: Item)

    /**
     * [RecyclerViewAdapter.onViewAttachedToWindow] 호출시 [.onViewAttachedToWindow] 을 호출 한다.
     */
    fun onViewAttachedToWindow() {
        // Nothing
    }

    /**
     * [RecyclerViewAdapter.onViewDetachedFromWindow] 호출시 [.onViewDetachedFromWindow] 을 호출 한다.
     */
    fun onViewDetachedFromWindow() {
        // Nothing
    }

    /**
     * [RecyclerViewAdapter.onBindViewHolder] 호출시 [.onBindViewHolder] 을 호출 한다.
     */
    fun onPreBindViewHolder() {
        onBindArguments(getArgumentsInternal())
    }

    /**
     * [RecyclerViewAdapter.onBindViewHolder] 호출시 [.onPreBindViewHolder] 을 호출 한다.
     *
     * @param item
     */
    fun onBindViewHolder(item: Item) {
        if (mFirstLoad) {
            mFirstLoad = false
        }
        onBindArguments(getArgumentsInternal())
        if (this.item == null || this.item == item == false) {
            this.item = item
            isItemChange = true
        } else {
            isItemChange = false
        }
        onBind()
        onRefresh(item)
    }

    /**
     * [RecyclerViewAdapter.onViewRecycled] 호출시 [.release] 을 호출 한다.
     */
    fun onViewRecycled() {
        release()
        item = null
        mFirstLoad = true
    }

    /**
     * [RecyclerViewAdapter.onCreateViewHolder] 호출
     *
     * @param viewType
     */
    fun onCreateViewHolder(viewType: Int) {}
    //======================================================================
    // Protected Methods
    //======================================================================
    /**
     * [RecyclerViewHolder] View 생성시 호출
     *
     * @param view
     * @see RecyclerViewAdapter.onCreateViewHolder
     */
    protected fun onCreateView(view: View) {}

    /**
     * [.onBindViewHolder] 연결시 호출
     *
     * @see {@link RecyclerViewAdapter.onBindViewHolder
     */
    protected fun onBind() {
        // Override
    }

    /**
     * [RecyclerViewHolder] 연결시 전달할 인자값
     *
     * [.onBind] 보다 먼저 호출된다.
     *
     * @param arguments [전달된 값][Bundle]
     */
    fun onBindArguments(arguments: Bundle) {}
    val resources: Resources
        get() = context.resources
    val context: Context
        get() = itemView.context

    fun findViewById(id: Int): View {
        return itemView.findViewById(id)
    }

    val arguments: Bundle
        get() = getArgumentsInternal()

    /**
     * 리소스 해제
     */
    fun release() {
        release(this.itemView)
    }

    //======================================================================
    // Private Methods
    //======================================================================

    fun getArgumentsInternal(): Bundle {
        if (mArguments == null) {
            mArguments = Bundle()
            return mArguments
        }
        return mArguments
    }

    private fun release(rootView: View?) {
        if (rootView == null) {
            return
        }
        if (rootView is ViewGroup) {
            val groupView = rootView
            val childCount = groupView.childCount
            for (index in 0 until childCount) {
                release(groupView.getChildAt(index))
            }
        }
    }

    companion object {
        fun buildItemView(recyclerView: RecyclerView, itemViewId: Int): View {
            return LayoutInflater.from(recyclerView.context)
                .inflate(itemViewId, recyclerView, false)
        }
    }

}