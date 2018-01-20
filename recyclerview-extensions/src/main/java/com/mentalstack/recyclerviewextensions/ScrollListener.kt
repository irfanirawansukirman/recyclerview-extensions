package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

/**
 * Created by aleksandrovdenis on 15.01.2018.
 */

internal enum class PagieDirection { START, END }

internal class ScrollListener(private val adapter: RecyclerAdapter) : RecyclerView.OnScrollListener() {

    private var dX = 0
    private var dY = 0

    private var orientation: Int? = null

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView?.let { recycler ->
                if (orientation == null)
                    orientation = recycler.getOrientation()

                orientation?.let { orientation ->
                    calcPaginationDirection(recycler, orientation)?.let { direction ->
                        adapter.processPagination(direction)
                    }
                }
            }
            dX = 0
            dY = 0
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        dX += dx
        dY += dy
        super.onScrolled(recyclerView, dx, dy)
    }

    private fun calcPaginationDirection(recycler: RecyclerView, dir: Int) =
            when (dir) {
                LinearLayout.HORIZONTAL -> dX
                LinearLayout.VERTICAL -> dY
                else -> null
            }?.let { delta ->
                when {
                    delta < 0 && recycler.firstVibleIndex()?.let
                    { it < adapter.paginationSensitive } ?: false ->
                        PagieDirection.START
                    delta > 0 && recycler.lastVisibleIndex()?.let
                    { it > adapter.itemCount - adapter.paginationSensitive-1 } ?: false ->
                        PagieDirection.END
                    else -> null
                }
            }
}