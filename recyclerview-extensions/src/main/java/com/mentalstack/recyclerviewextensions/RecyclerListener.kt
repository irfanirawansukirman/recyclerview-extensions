package com.mentalstack.recyclerviewextensions

/**
 * Created by aleksandrovdenis on 23.01.2018.
 */

interface RecyclerListener : RecyclerHandler {
    override fun handleRecyclerState(state: RecyclerState) {
        when (state) {
            is RecyclerState.LoadEnded -> onLoadEnded(state.direction)
            is RecyclerState.LoadError -> onLoadError(state.direction)
            is RecyclerState.LoadStarted -> onLoadStarted(state.direction)
        }
    }

    fun onLoadStarted(direction: PaginatorDirection) {}
    fun onLoadEnded(direction: PaginatorDirection) {}
    fun onLoadError(direction: PaginatorDirection) {}
}