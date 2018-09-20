package com.mentalstack.recyclerviewextensions

sealed class RecyclerState {
    abstract fun stringify(): String

    class LoadStarted(val direction: PaginatorDirection) : RecyclerState() {
        override fun stringify(): String = "Load started. Dir=$direction"
    }

    class LoadEnded(val direction: PaginatorDirection, val countNew: Int?, val countTotal: Int) : RecyclerState() {
        override fun stringify(): String = "Load ended. Dir=$direction, new=$countNew, total=$countTotal"
    }

    class LoadError(val direction: PaginatorDirection) : RecyclerState() {
        override fun stringify(): String = "Load error. Dir=$direction"
    }
}

