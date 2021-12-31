package com.kursor.surprise.entities

import android.util.Log
import com.kursor.surprise.objects.Factions.FACTIONS
import com.kursor.surprise.objects.Factions.STAR_EMPIRE
import com.kursor.surprise.objects.Provinces
import com.kursor.surprise.objects.War

class Battle(val attacker: Faction, val defender: Faction, val province: Province) {


    fun won() {
        Log.i("Battle", "won")
        when (FACTIONS[STAR_EMPIRE]!!.id) {
            attacker.id -> {
                Log.i("Battle", "attacker")
                val suc = defender.provinces.remove(province)
                attacker.provinces.add(province)
                Log.i("Battle", "${attacker.provinces}\n${defender.provinces}\n$suc")
                Log.i("Battle", "${province.id in defender.provinces.map { it.id }}")
            }
            defender.id -> {
                Log.i("Battle", "defender")
                //TODO("your turn to attack province")
            }
        }
        War.update()

    }

    fun lost() {
        when (FACTIONS[STAR_EMPIRE]!!.id) {
            attacker.id -> {
                //TODO("enemy's turn to attack your province")
            }
            defender.id -> {
                attacker.provinces.add(province)
                defender.provinces.remove(province)
            }
        }
        War.update()
    }

    fun serialize() = "${attacker.id},${defender.id},${province.id}"

    companion object {
        fun deserialize(string: String): Battle {
            var str = string
            val attackerId = str.substring(0, str.indexOf(",")).toInt()
            str = str.substring(str.indexOf(",") + 1)
            val defenderId = str.substring(0, str.indexOf(",")).toInt()
            str = str.substring(str.indexOf(",") + 1)
            val provinceId = str.substring(str.indexOf(",") + 1).toInt()
            return Battle(
                FACTIONS[attackerId]!!,
                FACTIONS[defenderId]!!,
                Provinces.PROVINCES[provinceId]!!
            )
        }
    }
}