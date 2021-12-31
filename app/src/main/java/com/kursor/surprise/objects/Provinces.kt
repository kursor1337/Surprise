package com.kursor.surprise.objects

import android.graphics.Rect
import com.kursor.surprise.R
import com.kursor.surprise.entities.Province
import kotlin.math.abs
import kotlin.math.sqrt

object Provinces {

    val PROVINCES = mapOf(
        R.string.nadya_house to Province(R.string.nadya_house, Rect(1376, 2005, 1476, 2087)),
        R.string.danil_house to Province(R.string.danil_house, Rect(858, 1358, 960, 1430)),
        R.string.eldar_house to Province(R.string.eldar_house, Rect(1285, 1550, 1380, 1625)),
        R.string.sergey_house to Province(R.string.sergey_house, Rect(1013, 1355, 1115, 1426)),
        R.string.vanya_house to Province(R.string.vanya_house, Rect(1115, 1355, 1215, 1426)),
        R.string.sergey_old_yard to Province(
            R.string.sergey_old_yard,
            Rect(1090, 1555, 1188, 1634)
        ),
        R.string.vanya_old_yard to Province(R.string.vanya_old_yard, Rect(1186, 1558, 1283, 1627)),
        R.string.ignat_house to Province(R.string.ignat_house, Rect(1216, 868, 1314, 943)),
        R.string.casino to Province(R.string.casino, Rect(2310, 840, 2396, 910)),
        R.string.garazhnaya to Province(R.string.garazhnaya, Rect(1080, 1890, 1376, 2087)),
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

}