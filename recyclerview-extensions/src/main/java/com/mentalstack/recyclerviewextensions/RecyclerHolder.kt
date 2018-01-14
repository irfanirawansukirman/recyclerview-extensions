package com.mentalstack.recyclerviewextensions

import android.view.View

/**
 * Created by aleksandrovdenis on 14.01.2018.
 */
open class RecyclerHolder( val type:Int, val method: View.()->Unit){
    fun equals(pair:Pair<Int, View.()->Unit>) = type == pair.first && method == pair.second
}