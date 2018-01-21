package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder(
        override val layoutType: Int,
        override val bindMethod: ((View) -> Unit)) : IRecyclerHolder

class RecyclerHolderLayoutOnly(override val layoutType: Int) : IRecyclerHolder {
    override val bindMethod: ((View) -> Unit) = emptyMethod

    private companion object {
        val emptyMethod: ((View) -> Unit) = {}
    }
}