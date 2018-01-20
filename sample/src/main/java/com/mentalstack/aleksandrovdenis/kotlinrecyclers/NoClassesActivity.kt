package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.GenreData
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.getIconID
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.listData
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.layout_genre_cell_2.view.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */

class NoClassesActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.no_classes_rec)
        toolbar_back.setOnClickListener { finish() }
        constructScrollerData(listData)
    }

    private fun constructScrollerData(array: List<Map<String, Any>>) {
        multi_recycler.adapter.addPairs(
                array.map { R.layout.layout_genre_cell_2 to GenreData(it).constructPagieMethod() }
        )
    }

    private fun GenreData.constructPagieMethod(): (View) -> Unit = { view ->
        view.cell2_title.text = title
        view.cell2_icon.setImageResource(view.context.getIconID(icon))
        view.setOnClickListener { Toast.makeText(view.context, title, Toast.LENGTH_LONG).show() }
    }
}
