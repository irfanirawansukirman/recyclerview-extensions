package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import com.mentalstack.recyclerviewextensions.TwoWayPaginator
import java.util.*
import kotlin.concurrent.timerTask

/**
 * Created by aleksandrovdenis on 01.02.2018.
 */
class GenreDataTwoWayPaginator : TwoWayPaginator() {
    private var testCounter = 0
    private var alreadyLoaded = 0
    override val loadEnd: ((List<IRecyclerHolder>?) -> Unit) -> Unit
        get() = { onComplete ->
            val newList = listData.map { GenreData(it, testCounter++) }.map { it.constructViewCell2() }
            alreadyLoaded += newList.size

            Timer().schedule(timerTask { onComplete(newList) }, 100)

        }

    private var testCounterStart = 0
    private var alreadyLoadedStart = 0

    override val loadStart: ((List<IRecyclerHolder>?) -> Unit) -> Unit
        get() = { onComplete ->
            val newList = listData.map { GenreData(it, --testCounterStart) }.reversed().map { it.constructViewCell2() }
            alreadyLoadedStart += newList.size

            Timer().schedule(timerTask { onComplete(newList) }, 100)

        }

}