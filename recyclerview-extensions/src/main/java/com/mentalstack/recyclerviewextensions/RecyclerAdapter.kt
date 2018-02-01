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

    var listener: RecyclerListener? = null

    private var preloader: IRecyclerHolder? = null

    fun setPreloader(value: Int) = setPreloader(RecyclerHolderLayoutOnly(value))
    fun setPreloader(value: IRecyclerHolder) {
        this.preloader = value
    }

    private var error: IRecyclerHolder? = null
    fun setError(value: Int) = setError(RecyclerHolderLayoutOnly(value))
    fun setError(value: IRecyclerHolder) {
        this.error = value
    }

    private var endList: IRecyclerHolder? = null
    fun setEndList(value: Int) = setEndList(RecyclerHolderLayoutOnly(value))
    fun setEndList(value: IRecyclerHolder) {
        endList = value
    }

    var paginationSensitive = 1
        set(value) {
            if (value > 1) field = value
        }

    var startPaginator: (((List<IRecyclerHolder>?) -> Unit) -> Unit)? = null
        set(value) {
            field = value
            afterPaginatorAdded(PaginatorDirection.START)
        }

    var endPaginator: (((List<IRecyclerHolder>?) -> Unit) -> Unit)? = null
        set(value) {
            field = value
            afterPaginatorAdded(PaginatorDirection.END)
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
        notifyItemInserted(items.size - 1)
    }

    fun addPairs(list: List<Pair<Int, (View) -> Unit>>) = addAll(list.map { RecyclerHolder(it.first, it.second) })
    fun addAll(elements: List<IRecyclerHolder>) {
        items.addAll(elements)
        notifyItemRangeChanged(items.size - elements.size - 1, elements.size)
    }

    fun remove(index: Int) {
        if (index >= 0) {
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

    fun merge(
            mergeditems: List<IRecyclerHolder>,
            mergeFunc: (IRecyclerHolder, IRecyclerHolder) -> Boolean,
            addRestDirection: PaginatorDirection? = PaginatorDirection.END) {

        val notMergetItems = mutableListOf<IRecyclerHolder>()

        mergeditems.forEach { currItem ->
            items.firstOrNull { mergeFunc(currItem, it) }?.let { foundedItem ->
                val pos = items.indexOf(foundedItem)
                items[pos] = currItem
            } ?: notMergetItems.add(currItem)
        }

        addRestDirection?.let {
            val position = when (it) {
                PaginatorDirection.START -> 0
                PaginatorDirection.END -> items.size
            }
            items.addAll(position, notMergetItems)
        }

        safety {
            notifyDataSetChanged()
        }

    }

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

    internal fun processPagination(direction: PaginatorDirection) {
        if (!paginatorProcessed) {
            val func = when (direction) {
                PaginatorDirection.START -> startPaginator
                PaginatorDirection.END -> endPaginator
            } ?: return

            safety {
                paginatorProcessed = true
                listener?.onLoadStarted(direction)
                error?.let { remove(it) }
                endList?.let { remove(it) }
                preloader?.commonAdd(direction)
                func.invoke { finishLoad(direction, it) }
            }
        }
    }

    private fun afterPaginatorAdded(direction: PaginatorDirection) {
        if (items.isEmpty()) processPagination(direction)
    }

    private fun finishLoad(direction: PaginatorDirection, newItems: List<IRecyclerHolder>?) {
        newItems?.let {
            val index = if (direction == PaginatorDirection.START) 0 else items.size
            items.addAll(index, it)
            safety {
                notifyDataSetChanged()
                if (direction == PaginatorDirection.START) {
                    recycler?.scrollToTop(newItems.size)
                }
            }
        }
        paginatorProcessed = false

        safety {
            preloader?.let {
                print("test")
                remove(it)
            }
            if (newItems == null) {
                listener?.onLoadEnded(direction)
                listener?.onLoadError(direction)
                error?.commonAdd(direction)
            } else {
                listener?.onLoadEnded(direction)
                if (newItems.isEmpty())
                    endList?.commonAdd(direction)
            }
        }
    }

    private fun IRecyclerHolder.commonAdd(direction: PaginatorDirection) {
        if (items.contains(this)) {
            remove(this)
        }

        val position = when (direction) {
            PaginatorDirection.START -> 0
            PaginatorDirection.END -> items.size
        }
        items.add(position, this)
        notifyItemInserted(position)

    }

    private fun safety(method: () -> Unit) {
        recycler?.context?.runOnUiThread { method() }
    }
}

class AbstractViewHolder(val type: Int, view: View) : RecyclerView.ViewHolder(view)
