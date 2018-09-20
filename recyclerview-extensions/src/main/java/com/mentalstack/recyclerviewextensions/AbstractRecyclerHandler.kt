package com.mentalstack.recyclerviewextensions

internal class AbstractRecyclerHandler(val func: (RecyclerState) -> Unit) : RecyclerHandler {
    override fun handleRecyclerState(state: RecyclerState) = func(state)
}