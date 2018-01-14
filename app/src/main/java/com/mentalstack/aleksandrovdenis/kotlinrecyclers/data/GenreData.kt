package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.graphics.Color
import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.R
import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import kotlinx.android.synthetic.main.layout_genre_cell_1.view.*

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class GenreData : IRecyclerHolder {
    override val bindMethod: View.() -> Unit = {
        cell1_title.text = title
        cell1_icon.setImageResource(context.getIconID(icon))
        cell1_title.setTextColor(Color.BLACK)
        cell1_cont.setBackgroundColor(Color.parseColor(rawColor))
    }

    override fun getType(): Int = R.layout.layout_genre_cell_1

    val icon: String
    val title: String
    val rawColor: String

    constructor(obj: Map<String, Any>) {
        icon = (obj["icon"] as? String) ?: ""
        title = (obj["title"] as? String) ?: ""
        rawColor = (obj["color"] as? String) ?: ""
    }

    constructor(icon: String, title: String, rawColor: String) {
        this.icon = icon
        this.title = title
        this.rawColor = rawColor
    }
}

