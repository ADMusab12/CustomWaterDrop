package com.example.customwaterdrop

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class WaterDropProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var progress: Float = 0f
    private var dailyTarget: Float = 0f

    private val outerPaint = Paint().apply {
        color = Color.parseColor("#ADD8E6")
        style = Paint.Style.STROKE
        strokeWidth = 7f
        isAntiAlias = true
    }

    private val innerPaint = Paint().apply {
        color = Color.parseColor("#4169E1")
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    init {
        progress = getSavedProgress()
    }

    fun setDailyTarget(target: Float) {
        dailyTarget = target
    }

    fun updateProgress(quantity: Float) {
        progress += quantity / dailyTarget
        if (progress > 1.0f) progress = 1.0f
        saveProgress(progress)
        invalidate()
    }

    fun getProgress(): Float {
        return progress
    }

    private fun createDropPath(width: Float, height: Float): Path {
        return Path().apply {
            moveTo(width / 2, 0f)
            cubicTo(width, height / 3, width, height, width / 2, height)
            cubicTo(0f, height, 0f, height / 3, width / 2, 0f)
        }
    }

    private fun createProgressPath(width: Float, height: Float): Path {
        return Path().apply {
            moveTo(width / 2, height)
            cubicTo(0f, height, 0f, height - height * progress, width / 2, height - height * progress)
            cubicTo(width, height - height * progress, width, height, width / 2, height)
            close()
        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val width = width.toFloat()
        val height = height.toFloat()

        val dropPath = createDropPath(width, height)
        val progressPath = createProgressPath(width, height)
        canvas.drawPath(dropPath, outerPaint)

        canvas.save()
        canvas.clipPath(dropPath)
        canvas.drawPath(progressPath, innerPaint)
        canvas.restore()
    }

    private fun saveProgress(progress: Float) {
        val sharedPreferences = context.getSharedPreferences("WaterDropProgressBar", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("progress", progress)
        editor.apply()
    }

    private fun getSavedProgress(): Float {
        val sharedPreferences = context.getSharedPreferences("WaterDropProgressBar", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("progress", 0f)
    }
}
