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

    private val listeners = mutableSetOf<RecyclerHandler>()
    fun addListener(listener: RecyclerHandler) = listeners.add(listener)
    fun removeListener(listener: RecyclerHandler) = listeners.remove(listener)

    fun addListener(func: (RecyclerState) -> Unit) {
        listeners.forEach {
            if ((it as? AbstractRecyclerHandler)?.func == func) return
        }

        addListener(AbstractRecyclerHandler(func))
    }

    fun removeListener(func: (RecyclerState) -> Unit) {
        listeners.forEach {
            if ((it as? AbstractRecyclerHandler)?.func == func) {
                removeListener(it)
                return
            }
        }
    }

    private fun processListeners(state: RecyclerState) =
            listeners.forEach { it.handleRecyclerState(state) }

    private val scrollListener = ScrollListener(this)
    private var recycler: RecyclerView? = null
    private val items = mutableListOf<IRecyclerHolder>()
    private val visibleItems = mutableMapOf<AbstractViewHolder, IRecyclerHolder>()

    //---------------------------------------------
    //
    // Common UI
    //
    //---------------------------------------------
    private var loader: IRecyclerHolder? = null

    fun setLoader(value: Int) = setLoader(RecyclerHolderLayoutOnly(value))
    fun setLoader(value: IRecyclerHolder) {
        this.loader = value
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

    //---------------------------------------------
    //
    // Pagination
    //
    //---------------------------------------------
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

    //---------------------------------------------
    //
    // Interface methods
    //
    //---------------------------------------------

    fun search(func: (IRecyclerHolder) -> Boolean) = items.find { func(it) }
    fun indexOf(func: (IRecyclerHolder) -> Boolean) = items.indexOfFirst { func(it) }

    fun update(element: Pair<Int, (View) -> Unit>, oldElement: Pair<Int, (View) -> Unit>) {
        items.find { it.layoutType == oldElement.first && (it as? RecyclerHolder)?.bindMethod == oldElement.second }?.let {
            update(RecyclerHolder(element.first, element.second), it)
        }
    }

    fun update(element: IRecyclerHolder, oldElement: IRecyclerHolder) {
        update(element, items.indexOf(oldElement))
    }

    fun update(element: IRecyclerHolder, index: Int) {
        items[index] = element
        notifyItemChanged(index)
    }

    fun replace(element: IRecyclerHolder) {
        clear()
        add(element)
    }

    fun replace(elements: List<IRecyclerHolder>) {
        clear()
        addAll(elements)
    }

    fun add(value: Pair<Int, (View) -> Unit>, toStart: Boolean = false) =
            add(RecyclerHolder(value.first, value.second), toStart)

    fun add(type: Int, method: (View) -> Unit, toStart: Boolean = false) =
            add(RecyclerHolder(type, method), toStart)

    fun add(element: IRecyclerHolder, toStart: Boolean = false) {
        if (toStart) {
            items.add(0, element)
            notifyDataSetChanged()
        } else {
            items.add(element)
            notifyItemInserted(items.size - 1)
        }
    }

    fun addPairs(list: List<Pair<Int, (View) -> Unit>>, toStart: Boolean = false) =
            addAll(list.map { RecyclerHolder(it.first, it.second) }, toStart)

    fun addAll(elements: List<IRecyclerHolder>, toStart: Boolean = false) {
        if (toStart) {
            items.addAll(0, elements)
            notifyDataSetChanged()
        } else {
            items.addAll(elements)
            notifyItemRangeInserted(items.size - elements.size, elements.size)
        }
    }

    fun remove(index: Int) {
        if (index >= 0) {
            items.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun remove(element: IRecyclerHolder) = remove(items.indexOf(element))
    fun remove(element: Pair<Int, (View) -> Unit>) {
        remove(items.indexOfFirst {
            it.layoutType == element.first && (it as? RecyclerHolder)?.bindMethod == element.second
        })
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    fun merge(
            mergedItems: List<IRecyclerHolder>,
            mergeFunc: (IRecyclerHolder, IRecyclerHolder) -> Boolean,
            addRestDirection: PaginatorDirection? = PaginatorDirection.END) {

        val notMergedItems = mutableListOf<IRecyclerHolder>()

        mergedItems.forEach { currItem ->
            items.firstOrNull { mergeFunc(currItem, it) }?.let { foundedItem ->
                val pos = items.indexOf(foundedItem)
                items[pos] = currItem
            } ?: notMergedItems.add(currItem)
        }

        addRestDirection?.let {
            val position = when (it) {
                PaginatorDirection.START -> 0
                PaginatorDirection.END -> items.size
            }
            items.addAll(position, notMergedItems)
        }

        safety {
            notifyDataSetChanged()
        }

    }

    //--------------------------------------------
    //
    //--------------------------------------------

    override fun onViewRecycled(holder: AbstractViewHolder) {
        super.onViewRecycled(holder)
        visibleItems.remove(holder)?.detachFrom(holder.itemView)
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        val item = items.getOrNull(position) ?: throw Exception("bounds of list")
        if (holder.type != item.layoutType) throw Exception("unsupported type holder/item")

        visibleItems[holder] = item
        item.bindTo(holder.itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return items.getOrNull(position)?.layoutType ?: throw Exception("bounds of list")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return AbstractViewHolder(viewType, view)
    }

    override fun getItemCount(): Int = items.size

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recycler = recyclerView
        this.recycler?.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
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
                processListeners(RecyclerState.LoadStarted(direction))
                error?.let { remove(it) }
                endList?.let { remove(it) }
                loader?.commonAdd(direction)
                func.invoke { finishLoad(direction, it) }
            }
        }
    }

    private fun afterPaginatorAdded(direction: PaginatorDirection) {
        if (items.isEmpty()) processPagination(direction)
    }

    private fun finishLoad(direction: PaginatorDirection, newItems: List<IRecyclerHolder>?) {
        newItems?.let {
            val index = if (direction == PaginatorDirection.START)
                0
            else
                items.size

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
            loader?.let { remove(it) }
            processListeners(RecyclerState.LoadEnded(
                    direction = direction,
                    countNew = newItems?.count(),
                    countTotal = items.count()))
            if (newItems == null) {
                processListeners(RecyclerState.LoadError(direction))
                error?.commonAdd(direction)
            } else {
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

