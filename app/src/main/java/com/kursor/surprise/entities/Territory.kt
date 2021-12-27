package com.kursor.surprise.entities

import android.graphics.Color
import android.graphics.Rect

class Territory(val name: String, val rect: Rect, private var affiliationField: Affiliation) {

    val affiliation: Affiliation
        get() = affiliationField

    enum class Affiliation(val color: Int) {

        ALLY(Color.GREEN), ENEMY(Color.RED);
    }

    fun serialize(): String {
        return "{$name:(${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom}):$affiliation}"
    }

    fun wonBattle() {
        affiliationField = Affiliation.ALLY
    }

    fun lostBattle() {
        affiliationField = Affiliation.ENEMY
    }

    companion object {
        fun deserialize(string: String): Territory {
            val name = string.substring(1, string.indexOf(":"))
            val (x1, y1, x2, y2) = string.substring(
                string.indexOf("(") + 1,
                string.indexOf(")")
            ).split(", ").map { it.toInt() }
            val affiliation = Affiliation.valueOf(string.substring(string.indexOf(")") + 2))
            return Territory(name, Rect(x1, y1, x2, y2), affiliation)
        }

        fun serializeList(list: List<Territory>) = buildString {
            list.forEachIndexed { index, territory ->
                if (index == list.size - 1) append(territory.serialize())
                else append("${territory.serialize()};")
            }
        }

        fun deserializeList(string: String) = string.split(";").map { deserialize(it) }

    }
}

fun Collection<Territory>.findByName(name: String): Territory {
    forEach { if (it.name == name) return it }
    throw NoSuchElementException("There is no territory with such name")
}
