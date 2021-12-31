package com.kursor.surprise.entities

import com.kursor.surprise.Factions
import com.kursor.surprise.Factions.STAR_EMPIRE
import com.kursor.surprise.gson

class Battle(val attacker: Faction, val defender: Faction, val province: Province) {


    fun won() {
        when (Factions.FACTIONS[STAR_EMPIRE]) {
            attacker -> {
                attacker.provinces.add(province)
                defender.provinces.remove(province)
            }
            defender -> {
                TODO("your turn to attack province")
            }
        }
    }

    fun lost() {
        when (Factions.FACTIONS[STAR_EMPIRE]) {
            attacker -> {
                TODO("enemy's turn to attack your province")
            }
            defender -> {
                attacker.provinces.add(province)
                defender.provinces.remove(province)
            }
        }
    }

    fun serialize(): String = gson.toJson(this)

    companion object {
        fun deserialize(string: String): Battle = gson.fromJson(string, Battle::class.java)
    }
}