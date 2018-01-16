package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 15.01.2018.
 */
enum class PagieDirection { START, END, NONE }

interface IPaginator {
    fun canLoadEnd(): Boolean
    fun canLoadStart(): Boolean

    fun loadStart(onSuccess: (List<IRecyclerHolder>?) -> Unit)
    fun loadEnd(onSuccess: (List<IRecyclerHolder>?) -> Unit)
}

abstract class TwoSidePaginator : IPaginator {
    override fun canLoadEnd(): Boolean = true
    override fun canLoadStart(): Boolean = true

    abstract override fun loadStart(onSuccess: (List<IRecyclerHolder>?) -> Unit)
    abstract override fun loadEnd(onSuccess: (List<IRecyclerHolder>?) -> Unit)
}

abstract class EndPaginator : IPaginator {
    override fun canLoadEnd(): Boolean = true
    override fun canLoadStart(): Boolean = false
    override fun loadEnd(onSuccess: (List<IRecyclerHolder>?) -> Unit) {
        throw Exception("unhandled call from end pagination")
    }

    abstract override fun loadStart(onSuccess: (List<IRecyclerHolder>?) -> Unit)
}

abstract class StartPaginator : IPaginator {
    override fun canLoadEnd(): Boolean = false
    override fun canLoadStart(): Boolean = true
    override fun loadStart(onSuccess: (List<IRecyclerHolder>?) -> Unit) {
        throw Exception("unhandled call from end pagination")
    }

    abstract override fun loadEnd(onSuccess: (List<IRecyclerHolder>?) -> Unit)
}
