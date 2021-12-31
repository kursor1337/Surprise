package com.kursor.surprise.entities

import android.content.Context
import android.graphics.Color
import com.kursor.surprise.gson

class Faction(
    val id: Int,
    val provinces: MutableList<Province>,
    var relationship: Relationship,
    val color: Int
) {

    enum class Relationship(val color: Int) {
        NEUTRAL(Color.GRAY), ALLY(Color.GREEN), WAR(Color.RED)
    }

    fun serialize(): String = gson.toJson(this)

    fun localizedName(context: Context) = context.resources.getString(id)

    companion object {
        fun deserialize(string: String): Faction = gson.fromJson(string, Faction::class.java)
    }

}
