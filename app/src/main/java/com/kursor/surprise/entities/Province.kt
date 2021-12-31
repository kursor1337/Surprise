package com.kursor.surprise.entities

import android.content.Context
import android.graphics.Rect
import com.google.gson.reflect.TypeToken
import com.kursor.surprise.Factions
import com.kursor.surprise.gson
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

class Province(val id: Int, val rect: Rect) {

    fun serialize(): String = gson.toJson(this)

    fun findOwner(): Faction {
        Factions.FACTIONS.forEach { (id, faction) ->
            faction.provinces.forEach { province ->
                if (id == province.id) return faction
            }
        }
        throw IllegalStateException("Province is without an owner!")
    }

    fun localizedName(context: Context) = context.resources.getString(id)

    companion object {
        fun deserialize(string: String): Province = gson.fromJson(string, Province::class.java)

        fun serializeList(list: List<Province>) = gson.toJson(list)

        fun deserializeList(string: String): List<Province> = gson.fromJson(
            string, object : TypeToken<List<Province>>() {}.type
        )
    }
}

fun Collection<Province>.findProvinceByName(name: String, context: Context): Province {
    forEach { if (it.localizedName(context) == name) return it }
    throw NoSuchElementException("There is no province with such name")
}

fun Collection<Province>.findProvinceById(id: Int): Province {
    forEach { if (it.id == id) return it }
    throw IllegalArgumentException("There is no province with such id")
}
