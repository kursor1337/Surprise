package com.kursor.surprise

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import com.kursor.surprise.entities.Territory

object Tools {

    private val defaultTerritories = listOf(
        Territory("We", Rect(1010, 1350, 1220, 1425), Territory.Affiliation.ALLY),
        Territory("Danil", Rect(860, 1357, 960, 1430), Territory.Affiliation.ENEMY),
        Territory("Eldar", Rect(1280, 1555, 1380, 1630), Territory.Affiliation.ENEMY),
        Territory("Nadya", Rect(1375, 2005, 1480, 2085), Territory.Affiliation.ENEMY)
    )

    lateinit var territories: List<Territory>

    lateinit var pref: SharedPreferences
    var firstTime: Boolean = false


    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val temp = pref.getString(SAVE, "")
        territories = if (temp != "" && temp != null) {
            Territory.deserializeList(temp)
        } else ArrayList(defaultTerritories)
    }

    fun save() {
        val editor = pref.edit()
        editor.putString(TERR, Territory.serializeList(territories))
        editor.apply()
    }
}