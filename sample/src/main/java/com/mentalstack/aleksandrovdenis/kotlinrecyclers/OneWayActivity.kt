package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.GenreDataPaginator
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by aleksandrovdenis on 20.01.2018.
 */

class OneWayActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.one_way_paginator)
        toolbar_back.setOnClickListener { finish() }

        GenreDataPaginator().attachTo(multi_recycler.adapter)
    }
}
