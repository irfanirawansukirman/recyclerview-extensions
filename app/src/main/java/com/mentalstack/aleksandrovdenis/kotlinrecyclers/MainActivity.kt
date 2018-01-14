package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadJSON(baseContext, "music_genres.json")?.let { rawString ->
            parseString(rawString)?.let { rawObj ->
                (rawObj["objects"] as? ArrayList<Map<String, Any>>)?.let { rawArray ->
                    presentList(rawArray)
                }
            }
        }
    }

    private fun constructScrollerData(array: ArrayList<Map<String, Any>>) =
            array.map { rawElement ->
                val title = (rawElement["title"] as? String) ?: ""
                when {
                    title.contains("metal") -> GenreData(rawElement).constructViewCell2()
                    title.contains("rock") -> GenreData(rawElement).constructViewCell1()
                    title.contains("indie") -> GenreData2(rawElement).constructViewCell1()
                    else -> GenreData2(rawElement).constructViewCell2()
                }
            }


    private fun presentList(rawArray: ArrayList<Map<String, Any>>) {
        main_recycler.adapter.addPairs(constructScrollerData(rawArray))
    }


}
