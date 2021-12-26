package com.kursor.surprise

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class MapMenuView : View {
    private val mapBitmap: Bitmap
    private val paint = Paint()

    private val observers = mutableListOf<MapObserver>()

    interface MapObserver {
        fun onTerritoryClicked(territory: Territory)
    }

    constructor(context: Context): super(context) {
        mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.drawable_map_4k)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.drawable_map_4k)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        canvas.drawBitmap(mapBitmap, 0f, 0f, paint)

        Tools.territories.forEach { territory ->
            when (territory.affiliation) {
                Territory.Affiliation.ENEMY -> {
                    paint.color = Color.RED
                    canvas.drawRect(territory.rect, paint)
                }
                else -> {
                    paint.color = Color.GREEN
                    canvas.drawRect(territory.rect, paint)
                }
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        Tools.territories.forEach { territory ->
            if (territory.rect.contains(x, y)) observers.forEach { it.onTerritoryClicked(territory) }
        }
        return false
    }

    fun addObserver(observer: MapObserver) {
        observers.add(observer)
    }

}