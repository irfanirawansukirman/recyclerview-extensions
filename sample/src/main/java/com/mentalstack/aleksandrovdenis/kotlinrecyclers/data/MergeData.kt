package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.view.View
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.R
import com.mentalstack.recyclerviewextensions.IRecyclerHolder
import kotlinx.android.synthetic.main.layout_merge_cell.view.*

/**
 * Created by aleksandrovdenis on 01.02.2018.
 */

data class MergeData(val number: Int, val value: Int)

class MergeDataHolder(internal val data: MergeData) : IRecyclerHolder {
    companion object {
        fun equalizator(one: IRecyclerHolder, two: IRecyclerHolder): Boolean{
            val itemOne = one as? MergeDataHolder ?: return false
            val itemTwo = two as? MergeDataHolder ?: return false
            return itemOne.isEquals(itemTwo)
        }
    }

    override val layoutType: Int = R.layout.layout_merge_cell

    override fun bindTo(view: View) {
        view.mergeCell_value.text = data.value.toString()
        val text = "# ${data.number}"
        view.mergeCell_title.text = text
    }
}

fun MergeData.isEquals(other: MergeData) = (number == other.number)

fun MergeDataHolder.isEquals(other: MergeDataHolder) = data.isEquals(other.data)

