package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.loadData
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
            main_recycler.adapter.apply {
                add(constructCell(R.string.multirecycler, {
                    switchTo(MultiRecyclerActivity::class.java)
                }))
                add(constructCell(R.string.no_classes_rec, {
                    switchTo(NoClassesActivity::class.java)
                }))
                add(constructCell(R.string.one_way_paginator, {
                    switchTo(OneWayActivity::class.java)
                }))
                add(constructCell(R.string.two_way_paginator, {
                    Toast.makeText(this@MainActivity, "coming soon", Toast.LENGTH_LONG).show()
                }))
            }
        }

    }

    private fun switchTo(kClass: Class<*>) {
        val int = Intent(this, kClass)
        int.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(int)
    }

    private fun constructCell(title: Int, clickListener: () -> Unit): Pair<Int, (View) -> Unit> =
            R.layout.layout_main_cell to { view ->
                view.maincell_text.setText(title)
                view.setOnClickListener { clickListener() }
            }
}
