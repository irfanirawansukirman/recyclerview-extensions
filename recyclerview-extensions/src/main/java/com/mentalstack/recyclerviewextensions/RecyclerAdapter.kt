package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class RecyclerAdapter : RecyclerView.Adapter<AbstractViewHolder>() {
    protected val items = mutableListOf<Pair<Int, View.() -> Unit>>()

    fun add(type: Int, method: View.() -> Unit) {
        items.add(type to method)
        notifyItemChanged(items.size - 1)
    }

    fun add(list: List<Pair<Int, View.() -> Unit>>) {
        items.addAll(list)
        notifyItemRangeChanged( items.size - list.size - 1, list.size)
    }

    fun remove(index:Int){
        items.removeAt( index )
        notifyItemRemoved(index)
    }

    fun remove( element: Pair<Int, View.()->Unit>){
        items.indexOf(element).let {
            if(it > 0)
                remove(it)
        }
    }

    fun clear() = items.clear()

    override fun onBindViewHolder(holder: AbstractViewHolder?, position: Int) {
        val item = items.getOrNull(position) ?: throw Exception("bounds of list")
        if (holder?.type != item.first) throw Exception("unsupported type holder/item")
        val view = holder?.itemView ?: throw Exception("holder is null")

        item.second.invoke(view)
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.first ?: throw Exception("bounds of list")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return AbstractViewHolder(viewType, view)
    }

    override fun getItemCount(): Int = items.size
}

class AbstractViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)
