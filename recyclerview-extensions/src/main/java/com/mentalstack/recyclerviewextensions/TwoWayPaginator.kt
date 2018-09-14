package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */
abstract class TwoWayPaginator {
    abstract val loadEnd: (((List<IRecyclerHolder>?) -> Unit) -> Unit)
    abstract val loadStart: (((List<IRecyclerHolder>?) -> Unit) -> Unit)

    fun attachTo(adapter: RecyclerAdapter) {
        adapter.endPaginator = loadEnd
        adapter.startPaginator = loadStart
    }
}