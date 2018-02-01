# RecyclerView extensions
RecyclerView Extensions by Kotlin, Mentalstack

<img src="https://github.com/mentalstack/recyclerview-extensions/blob/master/example.gif" width="320"/>

now, available in Jcenter:

```JSON
compile 'com.mentalstack.android:recyclerview-extensions-kotlin:2.0'
```

Please, see example - it's really easy!

## Recycler Adapter
Can show any elements. Easy and fast.
```kotlin
val adapter = RecyclerAdapter()
recycler.adapter = adapter add( R.layout.layout_cell_1 to { view -> 
	//modify view as you wish 
}) 
```

or use another methods:
```kotlin
fun add(value: Pair<Int, (View) -> Unit>)
fun add(type: Int, method: (View) -> Unit)
fun add(element: IRecyclerHolder) 
fun addPairs(list: List<Pair<Int, (View) -> Unit>>)
fun addAll(elements: List<IRecyclerHolder>)
```

## MultiRecycler
RecyclerView with steroids.
Simple implementation from RecyclerAdapter, easiest layout management. Add from xml - adapter and layout manager already included!
```xml
  <com.mentalstack.recyclerviewextensions.MultiRecycler
        android:id="@+id/main_recycler"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:reverse = "false" 
        app:cells="1"
        app:direction="vertical" />
```

```kotlin
 main_recycler.adapter.add( /* some */ )
```
 - direction - layout direction, vertical | horisontal
 - reverse: boolean, optional, default - false
 - cells: cells in row. optional, default - 1

## IRecyclerHolder
Custom interface from adapter cells. Easy:
```kotlin
interface IRecyclerHolder {
    val layoutType: Int
    val bindMethod: (View) -> Unit
}
```
Examples contains any ways from use, see it.
Base implementation: RecyclerHolder, RecyclerHolderLayoutOnly

## Support cells
```xml
 <com.mentalstack.recyclerviewextensions.MultiRecycler
        android:id="@+id/multi_recycler"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:cell_end="@layout/layout_recycler_end"
        app:cell_error="@layout/layout_recycler_error"
        app:cell_preloader="@layout/layout_recycler_preloader"
        app:cells="1"
        app:direction="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/include" />
```
- cell_end - optional, showed in end of paginator load
- cell_error - optional, showed, if paginator returns null
- cell_preloader - preloader from weiting paginator

Use xml or programmaticaly methods:
```kotlin
	multi_recycler.adapter.setPreloader(R.layout.layout_recycler_preloader)
        // or
        multi_recycler.adapter.setPreloader( object :IRecyclerHolder{
            override val layoutType: Int 
                get() = R.layout.layout_recycler_preloader
            override val bindMethod: (View) -> Unit
                get() = {
                    //modify cell if need
                }
        })
```

## OneWayPaginator
Paginator from upload new elements from end.
override 
```kotlin 
   abstract val loadMore:(((List<IRecyclerHolder>?) -> Unit)->Unit)
```
to load any elements. Attach adapter:
```kotlin
class GenreDataPaginator : OneWayPaginator() {
    private var alreadyLoaded = 0
    override val loadMore: ((List<IRecyclerHolder>?) -> Unit) -> Unit
        get() = { onComplete ->
        val newList = listData.map { GenreData(it) }.map { it.constructViewCell2() }
        alreadyLoaded += newList.size
        onComplete(newList)
    }
}
//------------------------------------------
  GenreDataPaginator().attachTo(multi_recycler.adapter)
```

## replace method from update elements
Easiest way to update adapter elements. Call
```kotlin
scroller.adapter.merge( newList, awesomeQualsFunc, restDirection)
```
 - newslist = list of new or updated elements
 - awesomeQualsFunc = method from comparing elements
 - restDirection = set way to add new elements (or null, if you want skip it)
