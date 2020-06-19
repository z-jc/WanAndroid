package com.dq.ui.button

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class DrawablwButton(
    context: Context?,
    attrs: AttributeSet?
) : AppCompatButton(context, attrs) {
    override fun onDraw(canvas: Canvas) {
        var canvas = canvas
        canvas = getTopCanvas(canvas)
        super.onDraw(canvas)
    }

    private fun getTopCanvas(canvas: Canvas): Canvas {
        val drawables = compoundDrawables ?: return canvas
        var drawable = drawables[0] // 左面的drawable
        if (drawable == null) {
            drawable = drawables[2] // 右面的drawable
        }

        // float textSize = getPaint().getTextSize(); // 使用这个会导致文字竖向排下来
        val textSize = paint.measureText(text.toString())
        val drawWidth = drawable.intrinsicWidth
        val drawPadding = compoundDrawablePadding
        val contentWidth = textSize + drawWidth + drawPadding
        val leftPadding = (width - contentWidth).toInt()
        setPadding(0, 0, leftPadding, 0) // 直接贴到左边
        val dx = (width - contentWidth) / 2
        canvas.translate(dx, 0f) // 往右移动
        return canvas
    }
}