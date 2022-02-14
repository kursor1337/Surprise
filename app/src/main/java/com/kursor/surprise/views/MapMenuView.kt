package com.kursor.surprise.views

import android.content.Context
import android.graphics.*
import android.text.Layout
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.kursor.surprise.objects.Factions
import com.kursor.surprise.R
import com.kursor.surprise.entities.Faction
import com.kursor.surprise.entities.Province
import android.text.StaticLayout
import org.w3c.dom.Text
import kotlin.math.abs
import kotlin.math.max


const val TAG = "MapMenuView"

class MapMenuView : View {

    private val mapBitmap: Bitmap
    private val paint = Paint().apply {
        alpha = 180
    }

    private val multilineTextPaint = TextPaint().apply {
        textSize = 26f
        color = Color.BLACK
    }

    private val textPaint = TextPaint().apply {
        textAlign = Paint.Align.CENTER
        textSize = 26f
        color = Color.BLACK
    }

    private val textRect = Rect()

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

                val provinceName = province.localizedName(context)
                textPaint.getTextBounds(provinceName, 0, provinceName.length, textRect)

                if (textRect.width() < province.rect.width() && textRect.height() < province.rect.width()) {
                    canvas.drawText(
                        provinceName,
                        ((province.rect.right + province.rect.left) / 2).toFloat(),
                        ((province.rect.bottom + province.rect.top) / 2).toFloat(),
                        textPaint
                    )
                } else {
                    drawProvince(province, canvas, multilineTextPaint, provinceName)
                }

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


    private fun drawProvince(
        province: Province,
        canvas: Canvas,
        textPaint: TextPaint,
        text: String
    ) {
        val bounds = province.rect
        val left = bounds.left.toFloat()
        val top = bounds.top.toFloat()
        val right = bounds.right.toFloat()
        val bottom = bounds.bottom.toFloat()
        val height = bottom - top
        val measuringTextLayout = StaticLayout(
            text, textPaint,
            abs(right - left).toInt(),
            Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false
        )
        var line = 0
        val totalLineCount = measuringTextLayout.lineCount
        line = 0
        while (line < totalLineCount) {
            val lineBottom = measuringTextLayout.getLineBottom(line)
            if (lineBottom > height) {
                break
            }
            line++
        }
        line--
        if (line < 0) {
            return
        }
        val lineEnd: Int = try {
            measuringTextLayout.getLineEnd(line)
        } catch (t: Throwable) {
            text.length
        }
        var truncatedText = text.substring(0, max(0, lineEnd))
        if (truncatedText.length < 3) {
            return
        }
        if (truncatedText.length < text.length) {
            truncatedText = truncatedText.substring(0, max(0, truncatedText.length - 3))
            truncatedText += "..."
        }
        val drawingTextLayout = StaticLayout(
            truncatedText, textPaint, abs(
                right - left
            ).toInt(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false
        )
        canvas.save()
        canvas.translate(left, top)
        drawingTextLayout.draw(canvas)
        canvas.restore()
    }
}