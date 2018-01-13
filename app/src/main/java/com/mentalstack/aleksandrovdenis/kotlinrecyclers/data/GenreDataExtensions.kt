package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.graphics.Color
import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.R
import kotlinx.android.synthetic.main.layout_genre_cell_1.view.*
import kotlinx.android.synthetic.main.layout_genre_cell_2.view.*

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
fun GenreData.constructViewCell1() = Pair<Int, View.() -> Unit>(R.layout.layout_genre_cell_1,
        {
            cell1_title.text = title
            cell1_icon.setImageResource(context.getIconID(icon))
            cell1_title.setTextColor(Color.BLACK)
            cell1_cont.setBackgroundColor(Color.parseColor(rawColor))
        })

fun GenreData.constructViewCell2() = Pair<Int, View.() -> Unit>(R.layout.layout_genre_cell_2,
        {
            cell2_title.text = title
            cell2_title.setTextColor(Color.BLACK)
            cell2_icon.setImageResource(context.getIconID(icon))
            cell2_cont.setBackgroundColor(Color.parseColor(rawColor))
        })

fun GenreData2.constructViewCell1() = Pair<Int, View.() -> Unit>(R.layout.layout_genre_cell_1,
        {
            cell1_title.text = title
            cell1_icon.setImageResource(context.getIconID(icon))
            cell1_title.setTextColor(Color.parseColor(rawColor))
            cell1_cont.setBackgroundColor(Color.WHITE)
        })

fun GenreData2.constructViewCell2() = Pair<Int, View.() -> Unit>(R.layout.layout_genre_cell_2,
        {
            cell2_title.text = title
            cell2_icon.setImageResource(context.getIconID(icon))
            cell2_title.setTextColor(Color.parseColor(rawColor))
            cell2_cont.setBackgroundColor(Color.WHITE)
        })
