package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */
abstract class TwoWayPaginator {
    abstract fun loadEnd(onComplete: (List<IRecyclerHolder>?) -> Unit)
    abstract fun loadStart(onComplete: (List<IRecyclerHolder>?) -> Unit)

    fun attachTo(adapter: RecyclerAdapter) {
        adapter.endPaginator = this::loadEnd
        adapter.startPaginator = this::loadStart
    }
}