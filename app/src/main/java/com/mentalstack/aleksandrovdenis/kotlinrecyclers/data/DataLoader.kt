package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
fun loadJSON(context: Context, path: String): String? {
    return try {
        val `is` = context.assets.open(path)
        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        String(buffer)
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

private val gson by lazy { GsonBuilder().setPrettyPrinting().create() }
fun parseString(str: String): Map<String, Any>? {
    return try {
        gson.fromJson(str, object : TypeToken<Map<String, Any>>() {}.type)
    } catch (e: Exception) {
        null
    }
}

fun Context.getIconID(name: String) = resources.getIdentifier(name, "drawable", packageName)