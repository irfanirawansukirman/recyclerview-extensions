package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.MergeData
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.MergeDataHolder
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by aleksandrovdenis on 01.02.2018.
 */

class MergeActivity : Activity() {

    private var clickCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.merge)
        toolbar_back.setOnClickListener { finish() }

        merge((0..9).map { MergeData(it, clickCount) })

        toolbar_title.setOnClickListener {
            clickCount++
            val pos1 = (Math.random() * 5).toInt()
            val pos2 = 5 + (Math.random() * 5).toInt()

            merge(listOf(MergeData(pos1, clickCount), MergeData(pos2, clickCount)))
        }
    }

    private fun merge(items: List<MergeData>) {
        items.map { MergeDataHolder(it) }.let {
            multi_recycler.adapter.merge(it, { one, two ->
                MergeDataHolder.equalizator(one, two)
            })
        }

        items.asSequence().map { it.number }.joinToString(",", "add items ").let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        }
    }
}
