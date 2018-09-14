package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.LinearLayout

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
fun RecyclerView.setVertical(elementsInRow: Int, reverse: Boolean = false) {
    attachLayout(LinearLayout.VERTICAL, elementsInRow, reverse)
}

fun RecyclerView.setHorizontal(elementsInRow: Int, reverse: Boolean = false) {
    attachLayout(LinearLayout.HORIZONTAL, elementsInRow, reverse)
}

private fun RecyclerView.attachLayout(direction: Int, elementsInRow: Int, reverse: Boolean = false) {
    layoutManager = if (elementsInRow == 1)
        LinearLayoutManager(context, direction, reverse)
    else
        GridLayoutManager(context, elementsInRow, direction, reverse)

    setHasFixedSize(true)
}

fun RecyclerView.isReversed() = (layoutManager as? LinearLayoutManager)?.reverseLayout
        ?: (layoutManager as? GridLayoutManager)?.reverseLayout

fun RecyclerView.getOrientation() =
        (layoutManager as? LinearLayoutManager)?.orientation
                ?: (layoutManager as? GridLayoutManager)?.orientation

fun RecyclerView.getVisibleIndexes(): Pair<Int, Int>? {
    return (layoutManager as? LinearLayoutManager)?.let {
        Pair(it.findFirstVisibleItemPosition(), it.findLastVisibleItemPosition())
    } ?: (layoutManager as? GridLayoutManager)?.let {
        Pair(it.findFirstVisibleItemPosition(), it.findLastVisibleItemPosition())
    }
}

fun RecyclerView.elementsInRow() =
        (layoutManager as? LinearLayoutManager)?.let { 1 }
                ?: (layoutManager as? GridLayoutManager)?.spanCount


fun RecyclerView.endVisibleIndex() = endVisibleIndex(null)
fun RecyclerView.endVisibleIndex(reversed: Boolean?) =
        if (reversed ?: isReversed() == true) {
            (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                    ?: (layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition()
        } else {
            (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                    ?: (layoutManager as? GridLayoutManager)?.findLastVisibleItemPosition()
        }

fun RecyclerView.startVisibleIndex(reversed: Boolean?) =
        if (reversed ?: isReversed() == true) {
            (layoutManager as? LinearLayoutManager)?.findLastVisibleItemPosition()
                    ?: (layoutManager as? GridLayoutManager)?.findLastVisibleItemPosition()
        } else {
            (layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                    ?: (layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition()
        }

fun RecyclerView.startVisibleIndex() = startVisibleIndex(null)

fun RecyclerView.scrollToTop(elementNum: Int) {
    (layoutManager as? LinearLayoutManager)?.let {
        val offset = if (it.orientation == LinearLayout.VERTICAL)
            height
        else
            width
        it.scrollToPositionWithOffset(elementNum, offset)
    }
    (layoutManager as? GridLayoutManager)?.let {
        val offset = if (it.orientation == LinearLayout.VERTICAL)
            height
        else
            width
        it.scrollToPositionWithOffset(elementNum, offset)
    }
}
