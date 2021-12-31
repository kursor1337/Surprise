package com.kursor.surprise.objects

import android.util.Log
import com.kursor.surprise.R
import com.kursor.surprise.entities.Battle
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.entities.Province
import com.kursor.surprise.objects.Factions.DOUBLE_VANYA_TEAM
import com.kursor.surprise.objects.Factions.STAR_EMPIRE
import java.lang.IllegalStateException
import kotlin.math.abs
import kotlin.math.sqrt

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
        var min = 3840 * 3840 + 2160 * 2160
        var prov = starEmpire.provinces[0]
        curFaction.provinces.forEach { curFactionProvince ->
            starEmpire.provinces.forEach { starEmpireProvince ->
                val dx = minOf(
                    abs(curFactionProvince.rect.right - starEmpireProvince.rect.left),
                    abs(starEmpireProvince.rect.right - curFactionProvince.rect.left)
                )
                val dy = minOf(
                    abs(curFactionProvince.rect.top - starEmpireProvince.rect.bottom),
                    abs(starEmpireProvince.rect.top - curFactionProvince.rect.bottom)
                )
                val cur = dx * dx + dy * dy
                if (cur < min) {
                    min = cur
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
            Factions.STAR_EMPIRE
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