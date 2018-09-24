package com.mentalstack.recyclerviewextensions

import android.view.View

fun RecyclerAdapter.add(value: Pair<Int, (View) -> Unit>, toStart: Boolean = false) =
        add(RecyclerHolder(value.first, value.second), toStart)

fun RecyclerAdapter.add(type: Int, method: (View) -> Unit, toStart: Boolean = false) =
        add(RecyclerHolder(type, method), toStart)

fun RecyclerAdapter.addPairs(list: List<Pair<Int, (View) -> Unit>>, toStart: Boolean = false) =
        addAll(list.map { RecyclerHolder(it.first, it.second) }, toStart)


