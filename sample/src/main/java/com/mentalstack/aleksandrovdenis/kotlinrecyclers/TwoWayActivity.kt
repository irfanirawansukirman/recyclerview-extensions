package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.GenreDataTwoWayPaginator
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by aleksandrovdenis on 01.02.2018.
 */

class TwoWayActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.two_way_paginator)
        toolbar_back.setOnClickListener { finish() }

        GenreDataTwoWayPaginator().attachTo(multi_recycler.adapter)
    }
}
