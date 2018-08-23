package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder(
        override val layoutType: Int,
        val bindMethod: (View) -> Unit) : IRecyclerHolder{
    override fun bindTo(view: View) = bindMethod.invoke(view)
}

class RecyclerHolderLayoutOnly(override val layoutType: Int) : IRecyclerHolder {
    override fun bindTo(view: View) {}
}