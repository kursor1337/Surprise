package com.kursor.surprise.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kursor.surprise.objects.Factions
import com.kursor.surprise.R
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.entities.Province

const val TAG = "MapMenuView"

class MapMenuView : View {

    private val mapBitmap: Bitmap
    private val paint = Paint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 26f
    }

    private val observers = mutableListOf<MapObserver>()

    interface MapObserver {
        fun onProvinceClicked(faction: Faction, province: Province)
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

        Factions.FACTIONS.forEach { (_, faction) ->
            faction.provinces.forEach { province ->
                paint.color = when (faction.relationship) {
                    Faction.Relationship.ALLY -> Color.GREEN
                    Faction.Relationship.NEUTRAL -> faction.color
                    Faction.Relationship.WAR -> Color.RED
                }
                paint.alpha = 180
                canvas.drawRect(province.rect, paint)
                paint.alpha = 255
                paint.color = Color.BLACK
                canvas.drawText(
                    province.localizedName(context),
                    ((province.rect.right + province.rect.left) / 2).toFloat(),
                    ((province.rect.bottom + province.rect.top) / 2).toFloat(),
                    paint
                )
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        Factions.FACTIONS.forEach { (_, faction) ->
            faction.provinces.forEach { province ->
                if (province.rect.contains(x, y)) {
                    observers.forEach { it.onProvinceClicked(faction, province) }
                }
            }

        }
        return super.onTouchEvent(event)
    }

    fun addObserver(observer: MapObserver) {
        observers.add(observer)
    }
}