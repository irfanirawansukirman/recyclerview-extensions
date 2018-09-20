package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.loadData
import com.mentalstack.recyclerviewextensions.IRecyclerHolder
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
            main_recycler.adapter.addAll(listOf(
                    MainCell(R.string.multirecycler, MultiRecyclerActivity::class.java, this),
                    MainCell(R.string.no_classes_rec, NoClassesActivity::class.java, this),
                    MainCell(R.string.one_way_paginator, OneWayActivity::class.java, this),
                    MainCell(R.string.two_way_paginator, TwoWayActivity::class.java, this),
                    MainCell(R.string.merge, MergeActivity::class.java, this),
                    MainCell(R.string.reversed, ReversedActivity::class.java, this),
                    MainCell(R.string.test_add, TestAddActivity::class.java, this),
                    MainCell(R.string.actions, EventsActivity::class.java, this)
            ))
        }
    }

    class MainCell<T : Activity>(private val strId: Int, private val clazz: Class<T>, private val router: MainActivity) : IRecyclerHolder {
        override val layoutType: Int = R.layout.layout_main_cell

        override fun bindTo(view: View) {
            view.maincell_text.setText(strId)
            view.setOnClickListener {
                val int = Intent(router, clazz)
                int.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                router.startActivity(int)
            }
        }
    }
}
