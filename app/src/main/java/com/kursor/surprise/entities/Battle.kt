package com.kursor.surprise.entities

import com.kursor.surprise.gson

class Battle(val attacker: Faction, val defender: Faction, val territory: Territory) {

    fun serialize(): String = gson.toJson(this)

    companion object {
        fun deserialize(string: String): Battle = gson.fromJson(string, Battle::class.java)
    }
}