package com.kursor.surprise

import android.content.Context
import android.graphics.Color
import com.google.gson.reflect.TypeToken
import com.kursor.surprise.Provinces
import com.kursor.surprise.R
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.gson
import java.lang.IllegalArgumentException

object Factions {

    const val NADYA = R.string.nadya
    const val STAR_EMPIRE = R.string.star_empire
    const val DANIL = R.string.danil
    const val ELDAR = R.string.eldar
    const val DOUBLE_VANYA_TEAM = R.string.double_vanya

    val FACTION_NAMES = listOf<Int>(NADYA, STAR_EMPIRE, DANIL, ELDAR, DOUBLE_VANYA_TEAM)

    //-------------------------------------------------------------------

    val FACTIONS = mapOf<Int, Faction>(
        NADYA to Faction(
            NADYA,
            mutableListOf(),
            Faction.Relationship.NEUTRAL,
            Color.CYAN
        ),
        STAR_EMPIRE to Faction(
            STAR_EMPIRE,
            mutableListOf(),
            Faction.Relationship.ALLY,
            Color.GREEN
        ),
        DANIL to Faction(
            DANIL,
            mutableListOf(),
            Faction.Relationship.NEUTRAL,
            Color.BLUE
        ),
        ELDAR to Faction(
            ELDAR,
            mutableListOf(),
            Faction.Relationship.NEUTRAL,
            Color.GRAY
        ),
        DOUBLE_VANYA_TEAM to Faction(
            DOUBLE_VANYA_TEAM,
            mutableListOf(),
            Faction.Relationship.NEUTRAL,
            Color.MAGENTA
        )
    )

    fun defaultInit() {
        FACTIONS[NADYA]?.provinces?.let {
            it.add(Provinces.PROVINCES[R.string.nadya_house]!!)
            it.add(Provinces.PROVINCES[R.string.garazhnaya]!!)
        }
        FACTIONS[STAR_EMPIRE]?.provinces?.let {
            it.add(Provinces.PROVINCES[R.string.sergey_house]!!)
            it.add(Provinces.PROVINCES[R.string.sergey_old_yard]!!)
            it.add(Provinces.PROVINCES[R.string.vanya_house]!!)
            it.add(Provinces.PROVINCES[R.string.vanya_old_yard]!!)
            it.add(Provinces.PROVINCES[R.string.casino]!!)
        }
        FACTIONS[DANIL]?.provinces?.let {
            it.add(Provinces.PROVINCES[R.string.danil_house]!!)
        }
        FACTIONS[ELDAR]?.provinces?.let {
            it.add(Provinces.PROVINCES[R.string.eldar_house]!!)
        }
        FACTIONS[DOUBLE_VANYA_TEAM]?.provinces?.let {
            it.add(Provinces.PROVINCES[R.string.ignat_house]!!)
            it.add(Provinces.PROVINCES[R.string.shatokh_house]!!)
        }
    }

    fun initFromSaved(string: String) {
        val factionMap = gson.fromJson<Map<Int, Faction>>(
            string,
            object : TypeToken<Map<Int, Faction>>() {}.type
        )
        FACTION_NAMES.forEach {
            FACTIONS[it]?.provinces?.addAll(factionMap[it]!!.provinces)
            FACTIONS[it]?.relationship = factionMap[it]!!.relationship
        }
    }

    fun serialize(): String = gson.toJson(FACTIONS)

    fun findFactionByName(name: String, context: Context): Faction {
        FACTIONS.forEach { (_, faction) ->
            if (name == faction.localizedName(context)) return faction
        }
        throw IllegalArgumentException("There is no such faction")
    }
}