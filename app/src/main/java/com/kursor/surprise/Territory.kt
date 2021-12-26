package com.kursor.surprise

import android.graphics.Rect
import java.lang.IllegalArgumentException
import java.lang.StringBuilder

class Territory(val name: String, val rect: Rect, val affiliation: Affiliation) {

    enum class Affiliation {
        ALLY, ENEMY;
    }

    fun serialize(): String {
        return "{$name:(${rect.left}, ${rect.top}, ${rect.right}, ${rect.bottom}):$affiliation}"
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
