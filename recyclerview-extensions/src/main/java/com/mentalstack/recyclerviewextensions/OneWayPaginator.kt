package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */
abstract class oneWayPaginator{
    abstract fun loadMore(onComplete: (List<IRecyclerHolder>?) -> Unit)

    fun attachTo(adapter: RecyclerAdapter) {
        adapter.endPaginator = this::loadMore
    }
}