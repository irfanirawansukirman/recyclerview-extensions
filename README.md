# RecyclerView extensions from Kotlin
RecyclerView Extensions, Mentalstack

<img src="https://github.com/mentalstack/recyclerview-extensions/blob/master/device-2018-01-13-162829.gif" width="200"/>

<h2>Recycler Adapter</h2>
<p>Can show any elements. Easy and fast.</p>
```kotlin
val adapter = RecyclerAdapter()
recycler.adapter = adapter
add( R.layout.layout_cell_1 to { view ->
    //modify view as you wish
})
```

