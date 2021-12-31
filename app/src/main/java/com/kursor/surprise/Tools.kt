package com.kursor.surprise

import android.content.Context
import android.content.SharedPreferences

object Tools {

    lateinit var pref: SharedPreferences
    var firstTime: Boolean = false


    fun init(context: Context) {
        pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        val temp = pref.getString(SAVE, "")
        if (temp != "" && temp != null) {
            Factions.initFromSaved(temp)
        } else Factions.defaultInit()
    }

    fun save() {
        val editor = pref.edit()
        editor.putString(SAVE, Factions.serialize())
        editor.apply()
    }
}