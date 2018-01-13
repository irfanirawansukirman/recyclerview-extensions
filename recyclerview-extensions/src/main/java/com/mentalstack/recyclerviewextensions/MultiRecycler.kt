package com.mentalstack.recyclerviewextensions

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class MultiRecycler : RecyclerView {
    constructor(context: Context) : super(context) {
        initThis()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        parseAttrs(attrs)
        initThis()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttrs(attrs)
        initThis()
    }

    val adapter = RecyclerAdapter()

    private fun initThis() {
        setAdapter(adapter)
    }

    private fun parseAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs,
                R.styleable.MultiRecycler, 0, 0)

        val direction = if (a.hasValue(R.styleable.MultiRecycler_direction))
            a.getInt(R.styleable.MultiRecycler_direction, 0)
        else null
        val cells = if (a.hasValue(R.styleable.MultiRecycler_cells))
            a.getInt(R.styleable.MultiRecycler_cells, 1)
        else 1

        direction?.let { direction ->
            if (direction == 1)
                setVertical(cells)
            else
                setHorizontal(cells)
        }

        a.recycle()
    }

}