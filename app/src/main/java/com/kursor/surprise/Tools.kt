package com.kursor.surprise

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect

object Tools {

    private const val PREF = "pref"
    private const val SAVE = "save"
    private const val TERR = "terr"

    private val defaultTerritories = listOf<Territory>(
        Territory("We", Rect(1100, 1350, 1220, 1420), Territory.Affiliation.ALLY),
        Territory("Danil", Rect(860, 1360, 960, 1430), Territory.Affiliation.ENEMY),
        Territory("Eldar", Rect(1280, 1560, 1380, 1630), Territory.Affiliation.ENEMY),
        Territory("Nadya", Rect(1370, 2010, 1480, 2080), Territory.Affiliation.ENEMY)
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