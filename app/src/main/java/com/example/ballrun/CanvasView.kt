package com.example.ballrun

import android.util.AttributeSet
import android.view.View
import android.content.Context
import android.graphics.*

class CanvasView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val paint: Paint
    private val bmp: Bitmap
    private var xpos = 0f
    private var ypos = 0f
    private var preX = 0f
    private var preY = 0f
    override fun onDraw(canvas: Canvas) {
        // 背景、半透明
        canvas.drawColor(Color.argb(125, 0, 0, 255))
        canvas.drawBitmap(
            bmp, (width / 2 + xpos).toInt(),
            (height / 2 + ypos).toInt(), paint
        )
    }

    fun setPosition(xp: Float, yp: Float) {
        val dT = 0.8f
        val ax = -xp * 2
        val ay = yp * 2
        xpos += preX * dT + ax * dT * dT
        preX += ax * dT
        ypos += preY * dT + ay * dT * dT
        preY += ay * dT

        // 再描画
        invalidate()
    }

    init {
        paint = Paint()
        bmp = BitmapFactory.decodeResource(resources, R.drawable.ball)
    }
}