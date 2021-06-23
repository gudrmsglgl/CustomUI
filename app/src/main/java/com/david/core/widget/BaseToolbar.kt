package com.david.core.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import com.david.core.R

class BaseToolbar : Toolbar {

    constructor(context: Context) : super(asThemeWrapper(context))

    constructor(context: Context, attrs: AttributeSet?) : super(asThemeWrapper(context), attrs)

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(asThemeWrapper(context), attrs, defStyleAttr)

    fun setStatusBarMargin(margin: Boolean) {
        if (margin) {
            if (layoutParams is MarginLayoutParams) {
                (layoutParams as MarginLayoutParams).topMargin = getStatusBarHeight(context)
            }
        }
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return context.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    fun setStatusBarPadding(margin: Boolean) {
        if (margin) {
            clipToPadding = false
            setPadding(0, getStatusBarHeight(context), 0, 0)
        }
    }

    companion object {
        @JvmStatic
        @BindingAdapter("setNavigationOnClickListener")
        fun onNavigationClicked(toolbar: Toolbar, clickListener: OnClickListener) {
            toolbar.setNavigationOnClickListener(clickListener)
        }

        @JvmStatic
        @BindingAdapter("setStatusBarMarin")
        fun setStatusBarMargin(toolbar: BaseToolbar, margin: Boolean){
            toolbar.setStatusBarMargin(margin)
        }

        @JvmStatic
        @BindingAdapter("setStatusBarPadding")
        fun setStatusBarPadding(toolbar: BaseToolbar, margin: Boolean) {
            toolbar.setStatusBarPadding(margin)
        }


        @JvmStatic
        fun asThemeWrapper(context: Context): Context {
            return ContextThemeWrapper(context, R.style.AppToolbarThemeWrapper)
        }
    }
}