package com.kursor.surprise.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kursor.surprise.R
import com.kursor.surprise.Tools
import com.kursor.surprise.entities.Territory

const val TAG = "MapMenuView"

class MapMenuView : View {

    private val mapBitmap: Bitmap
    private val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 26f
    }

    private val observers = mutableListOf<MapObserver>()

    interface MapObserver {
        fun onTerritoryClicked(territory: Territory)
    }

    constructor(context: Context) : super(context) {
        mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.drawable_map_4k)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mapBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.drawable_map_4k)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = mapBitmap.height
        val width = mapBitmap.width
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas == null) return

        canvas.drawBitmap(mapBitmap, 0f, 0f, paint)

        Tools.territories.forEach { territory ->
            paint.color = territory.faction.color
            paint.alpha = 180
            canvas.drawRect(territory.rect, paint)
            paint.color = Color.BLACK
            canvas.drawText(
                territory.name,
                ((territory.rect.right + territory.rect.left) / 2).toFloat(),
                ((territory.rect.bottom + territory.rect.top) / 2).toFloat(),
                paint
            )
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()
        Tools.territories.forEach { territory ->
            if (territory.rect.contains(x, y)) {
                observers.forEach { it.onTerritoryClicked(territory) }
            }
        }
        return super.onTouchEvent(event)
    }

    fun addObserver(observer: MapObserver) {
        observers.add(observer)
    }

    fun update()

}