package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class RecyclerAdapter : RecyclerView.Adapter<AbstractViewHolder>() {
    protected val items = mutableListOf<IRecyclerHolder>()

    fun add(element: IRecyclerHolder) {
        items.add(element)
        notifyItemChanged(items.size - 1)
    }

    fun add(value: Pair<Int, View.() -> Unit>) = add(RecyclerHolder(value.first, value.second))
    fun add(type: Int, method: View.() -> Unit) = add(RecyclerHolder(type, method))


    fun addAll(elements: List<IRecyclerHolder>) {
        items.addAll(elements)
        notifyItemRangeChanged(items.size - elements.size - 1, elements.size)
    }

    fun addPairs(list: List<Pair<Int, View.() -> Unit>>) = addAll(list.map { RecyclerHolder(it.first, it.second) })
    fun remove(index: Int) {
        if (index > 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun remove(element: RecyclerHolder) = items.indexOf(element).let { remove(it) }

    fun remove(element: Pair<Int, View.() -> Unit>) =
            items.indexOfFirst { it.equals(element) }.let { remove(it) }


    fun clear() = items.clear()

    override fun onBindViewHolder(holder: AbstractViewHolder?, position: Int) {
        val item = items.getOrNull(position) ?: throw Exception("bounds of list")
        if (holder?.type != item.getType()) throw Exception("unsupported type holder/item")
        val view = holder?.itemView ?: throw Exception("holder is null")

        item.bindMethod.invoke(view)
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.getType() ?: throw Exception("bounds of list")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return AbstractViewHolder(viewType, view)
    }

    override fun getItemCount(): Int = items.size
}

class AbstractViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)
