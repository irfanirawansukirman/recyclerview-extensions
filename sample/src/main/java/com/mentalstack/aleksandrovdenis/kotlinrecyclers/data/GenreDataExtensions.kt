package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.graphics.Color
import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.R
import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import com.mentalstack.recyclerviewextensions.RecyclerHolder
import kotlinx.android.synthetic.main.layout_genre_cell_1.view.*
import kotlinx.android.synthetic.main.layout_genre_cell_2.view.*
import kotlinx.android.synthetic.main.layout_genre_cell_3.view.*

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
fun GenreData.constructViewCell2() = RecyclerHolder(R.layout.layout_genre_cell_2,
        {
            it.apply {
                cell2_title.text = title
                cell2_title.setTextColor(Color.BLACK)
                cell2_icon.setImageResource(context.getIconID(icon))
                cell2_cont.setBackgroundColor(Color.parseColor(rawColor))
            }
        }) as IRecyclerHolder

fun GenreData2.constructViewCell1() = Pair<Int, (View) -> Unit>(R.layout.layout_genre_cell_1,
        {
            it.apply {
                cell1_title.text = title
                cell1_icon.setImageResource(context.getIconID(icon))
                cell1_title.setTextColor(Color.parseColor(rawColor))
                cell1_cont.setBackgroundColor(Color.WHITE)
            }
        })

fun GenreData2.constructViewCell2() = RecyclerHolder(R.layout.layout_genre_cell_2,
        {
            it.apply {
                cell2_title.text = title
                cell2_icon.setImageResource(context.getIconID(icon))
                cell2_title.setTextColor(Color.parseColor(rawColor))
                cell2_cont.setBackgroundColor(Color.WHITE)
            }
        })

fun GenreData2.constructViewCell3() = Pair<Int, (View) -> Unit>(R.layout.layout_genre_cell_3,
        {
            it.apply {
                cell3_title.text = title
                cell3_icon.setImageResource(context.getIconID(icon))
                cell3_title.setTextColor(Color.parseColor(rawColor))
                cell3_cont.setBackgroundColor(Color.WHITE)
            }
        })

