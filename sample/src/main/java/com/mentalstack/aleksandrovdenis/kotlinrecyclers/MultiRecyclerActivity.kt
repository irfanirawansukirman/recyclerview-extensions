package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.*
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.toolbar.*

class MultiRecyclerActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.multirecycler)
        toolbar_back.setOnClickListener { finish() }
        constructScrollerData(listData)
    }

    private fun constructScrollerData(array: List<Map<String, Any>>) =
            array.map { rawElement ->
                val title = (rawElement["title"] as? String) ?: ""
                when {
                    title.contains("metal") -> //as interface IRecyclerHolder
                        multi_recycler.adapter.add(GenreData2(rawElement).constructViewCell3())
                    title.contains("rock") -> // as data class with implementation
                        multi_recycler.adapter.add(GenreData(rawElement))
                    title.contains("indie") -> // as pair
                        multi_recycler.adapter.add(GenreData2(rawElement).constructViewCell1())
                    else -> //as simple class RecyclerHolder
                        multi_recycler.adapter.add(GenreData(rawElement).constructViewCell2())
                }
            }
}
