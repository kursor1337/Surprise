package com.kursor.surprise.objects

import android.graphics.Rect
import com.kursor.surprise.R
import com.kursor.surprise.entities.Province
import com.kursor.surprise.objects.Factions.STAR_EMPIRE
import kotlin.math.abs
import kotlin.math.sqrt

object Provinces {

    val PROVINCES = mapOf(
        //nadya
        R.string.nadya_house to Province(R.string.nadya_house, Rect(1376, 2005, 1476, 2087)),
        R.string.garazhnaya to Province(R.string.garazhnaya, Rect(1080, 1890, 1376, 2087)),
        R.string.near_the_boiler_house to Province(
            R.string.near_the_boiler_house,
            Rect(1810, 1750, 1960, 1850)
        ),
        R.string.mini_lake to Province(R.string.mini_lake, Rect(1584, 1620, 1730, 1750)),
        //danil
        R.string.danil_house to Province(R.string.danil_house, Rect(858, 1358, 960, 1430)),
        //eldar
        R.string.eldar_house to Province(R.string.eldar_house, Rect(1285, 1550, 1380, 1625)),
        R.string.narrow_passage to Province(R.string.narrow_passage, Rect(1285, 1625, 1584, 1680)),
        //star empire
        R.string.sergey_house to Province(R.string.sergey_house, Rect(1013, 1355, 1115, 1426)),
        R.string.vanya_house to Province(R.string.vanya_house, Rect(1115, 1355, 1215, 1426)),
        R.string.sergey_old_yard to Province(
            R.string.sergey_old_yard,
            Rect(1090, 1558, 1188, 1634)
        ),
        R.string.vanya_old_yard to Province(R.string.vanya_old_yard, Rect(1186, 1558, 1283, 1627)),
        R.string.casino to Province(R.string.casino, Rect(2310, 840, 2396, 910)),
        //double vanya
        R.string.ignat_house to Province(R.string.ignat_house, Rect(1216, 868, 1314, 943)),
        R.string.shatokh_house to Province(R.string.shatokh_house, Rect(2132, 841, 2221, 908))
    )


    fun distance(province1: Province, province2: Province): Double {
        val dx = minOf(
            abs(province1.rect.right - province2.rect.left),
            abs(province2.rect.right - province1.rect.left)
        )
        val dy = minOf(
            abs(province1.rect.top - province2.rect.bottom),
            abs(province2.rect.top - province1.rect.bottom)
        )
        val cur = dx * dx + dy * dy
        return sqrt(cur.toDouble())
    }

    fun hasAllProvinces(): Boolean {
        val starProvinces = Factions.FACTIONS[STAR_EMPIRE]!!.provinces
        val provinces = PROVINCES.values
        if (starProvinces.containsAll(provinces)) return true
        return false
    }

}