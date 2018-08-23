package com.mentalstack.aleksandrovdenis.kotlinrecyclers.data

import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.io.IOException

/**
 * Created by aleksandrovdenis on 13.01.2018.
 */
private fun loadJSON(context: Context, path: String): String? {
    return try {
        context.assets?.open(path)?.let { stream ->
            val buffer = ByteArray(stream.available())
            stream.read(buffer)
            stream.close()
            String(buffer)
        }
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

private val gson by lazy { GsonBuilder().setPrettyPrinting().create() }
private fun parseString(str: String): Map<String, Any>? {
    return try {
        gson.fromJson(str, object : TypeToken<Map<String, Any>>() {}.type)
    } catch (e: Exception) {
        null
    }
}

lateinit var listData: List<Map<String, Any>>
    private set

fun Context.getIconID(name: String) = resources.getIdentifier(name, "drawable", packageName)

fun loadData(baseContext: Context): Boolean {
    loadJSON(baseContext, "music_genres.json")?.let { rawString ->
        parseString(rawString)?.let { rawObj ->
            (rawObj["objects"] as? List<Map<String, Any>>)?.let {
                listData = it
                return true
            }
        }
    }
    return false
}
