package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

/**
 * Created by aleksandrovdenis on 15.01.2018.
 */

internal class ScrollListener(private val adapter: RecyclerAdapter) : RecyclerView.OnScrollListener() {

    private var dX = 0
    private var dY = 0

    private var orientation: Int? = null
    private var reversed: Boolean? = null

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            recyclerView?.let { recycler ->
                if (orientation == null)
                    orientation = recycler.getOrientation()

                if (reversed == null)
                    reversed = recycler.isReversed()

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

    private fun calcPaginationDirection(recycler: RecyclerView, dir: Int): PaginatorDirection? {
        val delta = when (dir) {
            LinearLayout.HORIZONTAL -> dX
            LinearLayout.VERTICAL -> dY
            else -> null
        } ?: return null

        if (reversed == true) {
            return when {
                delta < 0 && recycler.startVisibleIndex(reversed)?.let
                { it > adapter.itemCount - adapter.paginationSensitive - 1 } ?: false ->
                    PaginatorDirection.END
                delta > 0 && recycler.endVisibleIndex(reversed)?.let
                { it < adapter.paginationSensitive } ?: false ->
                    PaginatorDirection.START
                else -> null
            }
        }

        return when {
            delta < 0 && recycler.startVisibleIndex(reversed)?.let
            { it < adapter.paginationSensitive } ?: false ->
                PaginatorDirection.START
            delta > 0 && recycler.endVisibleIndex(reversed)?.let
            { it > adapter.itemCount - adapter.paginationSensitive - 1 } ?: false ->
                PaginatorDirection.END
            else -> null
        }
    }

}
