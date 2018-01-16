package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

/**
 * Created by aleksandrovdenis on 15.01.2018.
 */
internal class ScrollListener(
        private val adapter: RecyclerAdapter) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        recyclerView?.let { recycler ->
            recycler.getDirection()?.let { direction ->
                adapter.processPagination(calcPaginationDirection(recycler, direction, dx, dy))
            }
        }
    }

    private fun calcPaginationDirection(recycler: RecyclerView, dir: Int, dx: Int, dy: Int): PagieDirection {
        return when (dir) {
            LinearLayout.HORIZONTAL -> when {
                dx < 0 && recycler.firstVibleIndex() == 0 -> PagieDirection.START
                dx > 0 && recycler.lastVisibleIndex() == adapter.itemCount -> PagieDirection.END
                else -> PagieDirection.NONE
            }
            LinearLayout.VERTICAL -> when {
                dy < 0 && recycler.firstVibleIndex() == 0 -> PagieDirection.START
                dy > 0 && recycler.lastVisibleIndex() == adapter.itemCount -> PagieDirection.END
                else -> PagieDirection.NONE
            }
            else -> PagieDirection.NONE
        }
    }

}