package com.mentalstack.recyclerviewextensions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.runOnUiThread

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class RecyclerAdapter : RecyclerView.Adapter<AbstractViewHolder>() {
    private var paginator: IPaginator? = null
    fun addPaginator(value: IPaginator) {
        paginator = value
    }

    internal val items = mutableListOf<IRecyclerHolder>()

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

    private var recycler: RecyclerView? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recycler = recyclerView
        this.recycler?.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recycler?.removeOnScrollListener(scrollListener)
        this.recycler = null
    }

    var paginatorProcessed: Boolean = false
    private val scrollListener by lazy { ScrollListener(this) }

    internal fun processPagination(direction: PagieDirection?) {
        if (direction != null && !paginatorProcessed && paginator != null) {
            when (direction) {
                PagieDirection.START -> {
                    paginatorProcessed = true
                    paginator?.loadStart { finishLoad(direction, it) }
                }
                PagieDirection.END -> {
                    paginatorProcessed = true
                    paginator?.loadEnd { finishLoad(direction, it) }
                }
                else -> {
                }
            }
        }
    }

    private fun finishLoad(direction: PagieDirection, newItems: List<IRecyclerHolder>?) {
        newItems?.let {
            val index = if (direction == PagieDirection.START) 0 else items.size
            items.addAll(index, it)
            recycler?.context?.runOnUiThread {
                notifyDataSetChanged()
            }
        }
        paginatorProcessed = false
    }
}

//--------------------------------------------
//
// Interface methods
//
//---------------------------------------------

fun RecyclerAdapter.search(func: (IRecyclerHolder) -> Boolean) = items.find { func(it) }
fun RecyclerAdapter.indexOf(func: (IRecyclerHolder) -> Boolean) = items.indexOfFirst { func(it) }

fun RecyclerAdapter.update(element: IRecyclerHolder, oldElement: IRecyclerHolder) {
    items.indexOf(oldElement).let { update(element, it) }
}

fun RecyclerAdapter.update(element: IRecyclerHolder, index: Int) {
    items[index] = element
    notifyItemChanged(index)
}

fun RecyclerAdapter.add(element: IRecyclerHolder) {
    items.add(element)
    notifyItemChanged(items.size - 1)
}

fun RecyclerAdapter.add(value: Pair<Int, View.() -> Unit>) = add(RecyclerHolder(value.first, value.second))
fun RecyclerAdapter.add(type: Int, method: View.() -> Unit) = add(RecyclerHolder(type, method))

fun RecyclerAdapter.addAll(elements: List<IRecyclerHolder>) {
    items.addAll(elements)
    notifyItemRangeChanged(items.size - elements.size - 1, elements.size)
}

fun RecyclerAdapter.addPairs(list: List<Pair<Int, View.() -> Unit>>) = addAll(list.map { RecyclerHolder(it.first, it.second) })
fun RecyclerAdapter.remove(index: Int) {
    if (index > 0) {
        items.removeAt(index)
        notifyItemRemoved(index)
    }
}

fun RecyclerAdapter.remove(element: RecyclerHolder) = items.indexOf(element).let { remove(it) }
fun RecyclerAdapter.remove(element: Pair<Int, View.() -> Unit>) = items.indexOfFirst {
    it.getType() == element.first && it.bindMethod == element.second
}.let { remove(it) }


fun RecyclerAdapter.clear() = items.clear()


class AbstractViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)
