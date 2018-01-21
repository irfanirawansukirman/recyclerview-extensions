package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */

interface IRecyclerHolder {
    val layoutType: Int
    val bindMethod: (View) -> Unit
}