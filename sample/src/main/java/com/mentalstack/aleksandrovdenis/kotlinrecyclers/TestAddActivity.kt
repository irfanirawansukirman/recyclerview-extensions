package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import kotlinx.android.synthetic.main.activity_test_add.*
import kotlinx.android.synthetic.main.layout_main_cell.view.*
import kotlinx.android.synthetic.main.toolbar.*

class TestAddActivity : Activity() {
    private var bottomValue = 0
    private var topValue = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_add)

        toolbar_title.setText(R.string.test_add)
        toolbar_back.setOnClickListener { finish() }

        test_add_to_end.setOnClickListener {
            test_add_recycler.adapter.add(TestCell(topValue++), false)
        }

        test_add_to_start.setOnClickListener {
            test_add_recycler.adapter.add(TestCell(--bottomValue), true)
        }
    }

    private class TestCell(private val data: Int) : IRecyclerHolder {
        override val layoutType: Int = R.layout.layout_main_cell
        override fun bindTo(view: View) {
            view.maincell_text.text = data.toString()
        }
    }
}