/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti.shape

import android.graphics.*
import android.util.Log
import com.jdqm.graffiti.dp
import kotlin.math.*


/**
 * @FileName: RectilinearLine
 * @Description: 绘制直线
 * @Author: Jdqm
 * @Date: 2022/5/27 17:41
 */
class RectilinearLine : Shape() {
    private val TAG = "RectilinearLine"
    private var k = 0F
    private var d = 0F

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        paint.style = Paint.Style.FILL
        points.forEach {
            paint.color = Color.parseColor("#332F79FF")
            canvas.drawCircle(it.x, it.y, 14F.dp, paint)
            paint.color = Color.parseColor("#2F79FF")
            canvas.drawCircle(it.x, it.y, 5F.dp, paint)
        }
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#2F79FF")

        path?.run {
            paint.strokeWidth = 1F.dp
            canvas.drawPath(this, paint)

            if (isActive) {
                paint.color = Color.parseColor("#0D2F79FF")
                paint.strokeWidth = 28F.dp
                canvas.drawPath(this, paint)
            }
        }

    }

    override fun addPoint(point: PointF) {
        super.addPoint(point)
        if (path == null) {
            path = Path()
            path!!.moveTo(point.x, point.y)
        } else {
            path!!.lineTo(point.x, point.y)
        }

        if (isDrawDone()) {
            //y = kx + d, 算出斜率k，和d
            k = (points[1].y - points[0].y) / (points[1].x - points[0].x)
            d = points[0].y - k * points[0].x
            Log.d(TAG, "直线方程: y=${k}x + $d")
        }
    }

    override fun updatePath() {
        path?.run {
            reset()
            moveTo(points[0].x, points[0].y)
            lineTo(points[1].x, points[1].y)
        }

        k = (points[1].y - points[0].y) / (points[1].x - points[0].x)
        d = points[0].y - k * points[0].x
    }

    override fun checkInPath(x: Float, y: Float): Boolean {
        var leftX = Float.MAX_VALUE
        var rightX = Float.MIN_VALUE

        points.forEach {
            leftX = min(it.x, leftX)
            rightX = max(it.x, rightX)
        }
        val inX = x > leftX && x < rightX

        val distance = abs(k * x - y + d) / sqrt(1 + k.toDouble().pow(2.0))
        Log.d(TAG, "点到直线距离: $distance")

        return inX && distance < 14F.dp
    }

    /**
     * 直线两个端点即可完成绘制
     */
    override fun isDrawDone(): Boolean {
        return points.size >= 2
    }
}