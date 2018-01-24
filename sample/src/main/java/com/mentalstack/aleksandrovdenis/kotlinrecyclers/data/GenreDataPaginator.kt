package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import com.mentalstack.recyclerviewextensions.OneWayPaginator
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */
class GenreDataPaginator : OneWayPaginator() {

    private var alreadyLoaded = 0
    private var testCounter: Int = 0

    override val loadMore: ((List<IRecyclerHolder>?) -> Unit) -> Unit
        get() = { onComplete ->
            val newList = listData.map { GenreData(it, testCounter++) }.map { it.constructViewCell2() }
            alreadyLoaded += newList.size

            Timer().schedule(timerTask { onComplete(newList) }, 5000)

        }

}