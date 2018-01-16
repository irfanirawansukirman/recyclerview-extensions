package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.*
import com.mentalstack.recyclerviewextensions.add
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadJSON(baseContext, "music_genres.json")?.let { rawString ->
            parseString(rawString)?.let { rawObj ->
                (rawObj["objects"] as? ArrayList<Map<String, Any>>)?.let {
                    constructScrollerData(it)
                }
            }
        }
    }

    private fun constructScrollerData(array: ArrayList<Map<String, Any>>) =
            array.map { rawElement ->
                val title = (rawElement["title"] as? String) ?: ""
                when {
                    title.contains("metal") -> //as interface IRecyclerHolder
                        main_recycler.adapter.add(GenreData(rawElement).constructViewCell2())
                    title.contains("rock") -> // as data class with implementation
                        main_recycler.adapter.add(GenreData(rawElement))
                    title.contains("indie") -> // as pair
                        main_recycler.adapter.add(GenreData2(rawElement).constructViewCell1())
                    else -> //as simple class RecyclerHolder
                        main_recycler.adapter.add(GenreData2(rawElement).constructViewCell2())
                }
            }

}
