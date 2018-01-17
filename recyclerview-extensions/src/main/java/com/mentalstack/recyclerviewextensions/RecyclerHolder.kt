package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder(val layoutType: Int, method: View.() -> Unit) : IRecyclerHolder {
    override fun getType(): Int = layoutType
    override val bindMethod: View.() -> Unit = method
}