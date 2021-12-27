package com.kursor.surprise.entities

import android.graphics.Rect
import com.google.gson.reflect.TypeToken
import com.kursor.surprise.gson

class Territory(val name: String, val rect: Rect, var faction: Faction) {


    fun serialize(): String {
        return "{$name:(${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom}):${faction.name}}"
    }

    companion object {
        fun deserialize(string: String): Territory = gson.fromJson(string, Territory::class.java)
//        {
//            val name = string.substring(1, string.indexOf(":"))
//            val (x1, y1, x2, y2) = string.substring(
//                string.indexOf("(") + 1,
//                string.indexOf(")")
//            ).split(", ").map { it.toInt() }
//            val factionName = string.substring(string.indexOf(")" + 2))
//            return Territory(name, Rect(x1, y1, x2, y2), Factions.findFactionByName(factionName))
//        }

        fun serializeList(list: List<Territory>) = gson.toJson(list)
//            buildString {
//            list.forEachIndexed { index, territory ->
//                if (index == list.size - 1) append(territory.serialize())
//                else append("${territory.serialize()};")
//            }
//        }

        fun deserializeList(string: String): List<Territory> = gson.fromJson(
            string, object : TypeToken<List<Territory>>() {}.type
        )
        //string.split(";").map { deserialize(it) }

    }
}

fun Collection<Territory>.findByName(name: String): Territory {
    forEach { if (it.name == name) return it }
    throw NoSuchElementException("There is no territory with such name")
}
