package com.kursor.surprise.entities

import android.content.Context
import android.graphics.Color
import com.kursor.surprise.gson

class Faction(
    val name: String,
    val territories: MutableList<Territory>,
    var relationship: Relationship,
    val color: Int
) {

    constructor(
        resId: Int,
        context: Context,
        territories: MutableList<Territory>,
        relationship: Relationship,
        color: Int
    ) : this(
        context.getString(resId),
        territories,
        relationship,
        color
    )

    enum class Relationship(val color: Int) {
        NEUTRAL(Color.GRAY), ALLY(Color.GREEN), WAR(Color.RED)
    }

    fun serialize(): String = gson.toJson(this)

    companion object {
        fun deserialize(string: String): Faction = gson.fromJson(string, Faction::class.java)
        //{
//            var str = string
//            val name = str.substring(0, str.indexOf(","))
//            str = str.substring(str.indexOf(",") + 1)
//            val territories = Territory.deserializeList(
//                str.substring(0, str.indexOf(","))
//            ).toMutableList()
//            str = str.substring(str.indexOf(",") + 1)
//            val relationship = Relationship.valueOf(string.substring(string.indexOf("}") + 1))
//            str = str.substring(str.indexOf(",") + 1)
//            val color = str.toInt()
//            return Faction(name, territories, relationship, color)
//        }
    }

}
