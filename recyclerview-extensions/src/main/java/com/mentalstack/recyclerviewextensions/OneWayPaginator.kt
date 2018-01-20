package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */
abstract class OneWayPaginator{
    abstract val loadMore:(((List<IRecyclerHolder>?) -> Unit)->Unit)

    fun attachTo(adapter: RecyclerAdapter) {
        adapter.endPaginator = loadMore
    }
}