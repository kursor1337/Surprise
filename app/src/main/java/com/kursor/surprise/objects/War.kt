package com.kursor.surprise.objects

import android.util.Log
import com.kursor.surprise.entities.Battle
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.objects.Factions.STAR_EMPIRE
import java.lang.IllegalStateException
import kotlin.math.abs

object War {

    var battleCount = 0
    private var warFactions: List<Faction> = listOfWarFactions()
    var currentTurn = STAR_EMPIRE
        private set

    fun listOfWarFactions(): List<Faction> {
        val result = mutableListOf<Faction>()
        Factions.FACTIONS.forEach { (_, faction) ->
            if (faction.provinces.isEmpty()) faction.relationship = Faction.Relationship.NEUTRAL
            if (faction.relationship == Faction.Relationship.WAR) result.add(faction)
        }
        return result
    }

    fun aiNextBattle(): Battle {
        Log.i("Battle", "aiNextBattle()")
        if (currentTurn == STAR_EMPIRE) throw IllegalStateException("Your turn!")
        val curFaction = Factions.FACTIONS[currentTurn]!!
        val starEmpire = Factions.FACTIONS[STAR_EMPIRE]!!
        var min = (3840 * 3840 + 2160 * 2160).toDouble()
        var prov = starEmpire.provinces[0]
        curFaction.provinces.forEach { curFactionProvince ->
            starEmpire.provinces.forEach { starEmpireProvince ->
                val dist = Provinces.distance(curFactionProvince, starEmpireProvince)
                if (dist < min) {
                    min = dist
                    prov = starEmpireProvince
                }
            }
        }
        return Battle(curFaction, starEmpire, prov)
    }

    fun update() {
        battleCount++
        updateWarFactions()
        currentTurn = if (battleCount % (warFactions.size + 1) == 0) {
            STAR_EMPIRE
        } else enemyTurn()
    }

    fun enemyTurn(): Int {
        val queue = battleCount % (warFactions.size + 1) - 1
        return warFactions[queue].id
    }

    fun updateWarFactions() {
        warFactions = listOfWarFactions()
    }
}