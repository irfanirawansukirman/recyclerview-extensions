# RecyclerView extensions

RecyclerView Extensions by Kotlin, Mentalstack

<img src="https://github.com/mentalstack/recyclerview-extensions/blob/master/device-2018-01-13-162829.gif" width="200"/>

<h2>Recycler Adapter</h2>
Can show any elements. Easy and fast.

```
val adapter = RecyclerAdapter()
recycler.adapter = adapter add( R.layout.layout_cell_1 to { view -> 
	//modify view as you wish 
}) 
```

or use another methods:

```
fun add(value: Pair<Int, (View) -> Unit>)
fun add(type: Int, method: (View) -> Unit)
fun add(element: IRecyclerHolder) 
       
fun addPairs(list: List<Pair<Int, (View) -> Unit>>)
fun addAll(elements: List<IRecyclerHolder>)
```
