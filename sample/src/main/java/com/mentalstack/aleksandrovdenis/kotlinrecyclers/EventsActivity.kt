package com.mentalstack.aleksandrovdenis.kotlinrecyclers

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import com.mentalstack.aleksandrovdenis.kotlinrecyclers.data.GenreDataTwoWayPaginator
import kotlinx.android.synthetic.main.activity_multirecycler.*
import kotlinx.android.synthetic.main.toolbar.*

class EventsActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multirecycler)

        toolbar_title.setText(R.string.actions)
        toolbar_back.setOnClickListener { finish() }

        multi_recycler.adapter.addListener { state ->
            Toast.makeText(this, state.stringify(), Toast.LENGTH_LONG).show()
        }

        GenreDataTwoWayPaginator().attachTo(multi_recycler.adapter)
    }
}