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

    private var preloader: IRecyclerHolder? = null
    fun setPreloader(value: IRecyclerHolder) {
        this.preloader = value
    }

    fun setPreloader(value: Int) {
        this.preloader = RecyclerHolderLayoutOnly(value)
    }

    private var error: IRecyclerHolder? = null
    fun setError(value: IRecyclerHolder) {
        this.error = value
    }

    fun setError(value: Int) {
        this.error = RecyclerHolderLayoutOnly(value)
    }

    private var endList: IRecyclerHolder? = null
    fun setEndList(value: IRecyclerHolder) {
        endList = value
    }

    fun setEndList(value: Int) {
        endList = RecyclerHolderLayoutOnly(value)
    }

    var paginationSensitive = 1
        set(value) {
            if (value > 1) field = value
        }

    private var startPaginator: (((List<IRecyclerHolder>?) -> Unit) -> Unit)? = null
        set(value) {
            field = value
            afterPaginatorAdded(PagieDirection.START)
        }

    var endPaginator: (((List<IRecyclerHolder>?) -> Unit) -> Unit)? = null
        set(value) {
            field = value
            afterPaginatorAdded(PagieDirection.END)
        }

    private var paginatorProcessed: Boolean = false
    private val scrollListener = ScrollListener(this)

    private var recycler: RecyclerView? = null

    private val items = mutableListOf<IRecyclerHolder>()

    //--------------------------------------------
    //
    // Interface methods
    //
    //---------------------------------------------

    fun search(func: (IRecyclerHolder) -> Boolean) = items.find { func(it) }
    fun indexOf(func: (IRecyclerHolder) -> Boolean) = items.indexOfFirst { func(it) }

    fun update(element: Pair<Int, (View) -> Unit>, oldElement: Pair<Int, (View) -> Unit>) {
        items.find { it.layoutType == oldElement.first && it.bindMethod == oldElement.second }?.let {
            update(RecyclerHolder(element.first, element.second), it)
        }
    }

    fun update(element: IRecyclerHolder, oldElement: IRecyclerHolder) {
        items.indexOf(oldElement).let { update(element, it) }
    }

    fun update(element: IRecyclerHolder, index: Int) {
        items[index] = element
        notifyItemChanged(index)
    }

    fun add(value: Pair<Int, (View) -> Unit>) = add(RecyclerHolder(value.first, value.second))
    fun add(type: Int, method: (View) -> Unit) = add(RecyclerHolder(type, method))
    fun add(element: IRecyclerHolder) {
        items.add(element)
        notifyItemChanged(items.size - 1)
    }

    fun addPairs(list: List<Pair<Int, (View) -> Unit>>) = addAll(list.map { RecyclerHolder(it.first, it.second) })
    fun addAll(elements: List<IRecyclerHolder>) {
        items.addAll(elements)
        notifyItemRangeChanged(items.size - elements.size - 1, elements.size)
    }

    fun remove(index: Int) {
        if (index > 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun remove(element: IRecyclerHolder) = items.indexOf(element).let { remove(it) }
    fun remove(element: Pair<Int, (View) -> Unit>) {
        items.indexOfFirst {
            it.layoutType == element.first && it.bindMethod == element.second
        }.let { remove(it) }
    }

    fun clear() = items.clear()

    //--------------------------------------------
    //
    //--------------------------------------------

    override fun onBindViewHolder(holder: AbstractViewHolder?, position: Int) {
        val item = items.getOrNull(position) ?: throw Exception("bounds of list")
        if (holder?.type != item.layoutType) throw Exception("unsupported type holder/item")
        val view = holder?.itemView ?: throw Exception("holder is null")

        item.bindMethod.invoke(view)
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.layoutType ?: throw Exception("bounds of list")
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AbstractViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(viewType, parent, false)
        return AbstractViewHolder(viewType, view)
    }

    override fun getItemCount(): Int = items.size

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

    internal fun processPagination(direction: PagieDirection) {
        if (!paginatorProcessed) {
            val func = when (direction) {
                PagieDirection.START -> startPaginator
                PagieDirection.END -> endPaginator
            } ?: return

            paginatorProcessed = true
            func.invoke { finishLoad(direction, it) }
        }
    }

    private fun afterPaginatorAdded(direction: PagieDirection) {
        if (items.isEmpty()) processPagination(direction)
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

class AbstractViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)
