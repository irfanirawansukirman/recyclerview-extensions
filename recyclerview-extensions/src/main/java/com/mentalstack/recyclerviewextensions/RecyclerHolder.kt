package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder(
        override val layoutType: Int,
        override val bindMethod: (View) -> Unit) : IRecyclerHolder {
}