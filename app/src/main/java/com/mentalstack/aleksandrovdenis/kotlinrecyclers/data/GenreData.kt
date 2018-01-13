package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
class GenreData{
    val icon: String
    val title: String
    val rawColor: String

    constructor( obj:Map<String, Any>){
        icon = (obj["icon"] as? String) ?: ""
        title = (obj["title"] as? String) ?: ""
        rawColor = (obj["color"] as? String) ?: ""
    }

    constructor(icon: String, title: String, rawColor: String){
        this.icon = icon
        this.title = title
        this.rawColor = rawColor
    }
}

