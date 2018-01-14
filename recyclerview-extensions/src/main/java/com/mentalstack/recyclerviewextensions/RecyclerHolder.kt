package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder( val layoutType:Int, val method: View.()->Unit): IRecyclerHolder
{
    override fun getType(): Int = layoutType


    override val bindMethod: View.() -> Unit = method

    fun equals(pair:Pair<Int, View.()->Unit>) = layoutType == pair.first && method == pair.second
}