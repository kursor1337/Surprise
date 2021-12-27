package com.kursor.surprise.entities

import android.content.Context
import java.lang.IllegalArgumentException

object Factions {

    val FACTIONS = mutableListOf<Faction>()

    fun initFactions(context: Context) {
//        FACTIONS.add()
//        FACTIONS.add()
//        FACTIONS.add()
//        FACTIONS.add()
//        FACTIONS.add()
//        FACTIONS.add()
    }

    fun findFactionByName(name: String): Faction {
        FACTIONS.forEach {
            if (name == it.name) return it
        }
        throw IllegalArgumentException("There is no such faction")
    }
}