package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.loadData
import junit.framework.Test
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_main_cell.view.*

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (loadData(this)) {
            main_recycler.adapter.addPairs(listOf(
                    constructCell(R.string.multirecycler, MultiRecyclerActivity::class.java),
                    constructCell(R.string.no_classes_rec, NoClassesActivity::class.java),
                    constructCell(R.string.one_way_paginator, OneWayActivity::class.java),
                    constructCell(R.string.two_way_paginator, TwoWayActivity::class.java),
                    constructCell(R.string.merge, MergeActivity::class.java),
                    constructCell(R.string.reversed, ReversedActivity::class.java),
                    constructCell(R.string.test_add, TestAddActivity::class.java)
            ))
        }
    }

    private fun switchTo(kClass: Class<*>) {
        val int = Intent(this, kClass)
        int.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(int)
    }

    private fun <T> constructCell(title: Int, clazz: Class<T>): Pair<Int, (View) -> Unit> =
            R.layout.layout_main_cell to { view ->
                view.maincell_text.setText(title)
                view.setOnClickListener { switchTo(clazz) }
            }
}
