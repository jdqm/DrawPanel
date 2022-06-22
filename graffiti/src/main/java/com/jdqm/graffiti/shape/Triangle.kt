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
class Triangle : Shape() {
    private  val TAG = "Triangle"

    private var k1 = 0F
    private var d1 = 0F

    private var k2 = 0F
    private var d2 = 0F

    private var k3 = 0F
    private var d3 = 0F

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        paint.style = Paint.Style.FILL
        if (isActive) {
            points.forEach {
                paint.color = Color.parseColor("#332F79FF")
                canvas.drawCircle(it.x, it.y, 14F.dp, paint)
                paint.color = Color.parseColor("#2F79FF")
                canvas.drawCircle(it.x, it.y, 5F.dp, paint)
            }
        }
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#2F79FF")

        path?.run {
            paint.pathEffect = null
            paint.strokeWidth = 1F.dp
            canvas.drawPath(this, paint)

            if (isActive) {
                paint.pathEffect = CornerPathEffect(14F.dp)
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
            if (isDrawDone()) {
                path!!.close()

                //y = kx + d, 算出斜率k，和d
                k1 = (points[1].y - points[0].y) / (points[1].x - points[0].x)
                d1 = points[0].y - k1 * points[0].x

                k2 = (points[2].y - points[1].y) / (points[2].x - points[1].x)
                d2 = points[1].y - k2 * points[1].x

                k3 = (points[2].y - points[0].y) / (points[2].x - points[0].x)
                d3 = points[0].y - k3 * points[0].x

            }
        }
    }

    override fun updatePath() {
        path?.run {
            reset()
            moveTo(points[0].x, points[0].y)
            lineTo(points[1].x, points[1].y)
            lineTo(points[2].x, points[2].y)
            close()
        }

        //y = kx + d, 算出斜率k，和d
        k1 = (points[1].y - points[0].y) / (points[1].x - points[0].x)
        d1 = points[0].y - k1 * points[0].x

        k2 = (points[2].y - points[1].y) / (points[2].x - points[1].x)
        d2 = points[1].y - k2 * points[1].x

        k3 = (points[2].y - points[0].y) / (points[2].x - points[0].x)
        d3 = points[0].y - k3 * points[0].x
    }

    /**
     * 直线两个端点即可完成绘制
     */
    override fun isDrawDone(): Boolean {
        return points.size >= 3
    }

    override fun checkInPath(x: Float, y: Float): Boolean {

        var leftX = Float.MAX_VALUE
        var rightX = Float.MIN_VALUE

        points.forEach {
            leftX = min(it.x, leftX)
            rightX = max(it.x, rightX)
        }
        val inX = x > leftX && x < rightX

        val distance1 = abs(k1 * x - y + d1) / sqrt(1 + k1.toDouble().pow(2.0))
        val distance2 = abs(k2 * x - y + d2) / sqrt(1 + k2.toDouble().pow(2.0))
        val distance3 = abs(k3 * x - y + d3) / sqrt(1 + k3.toDouble().pow(2.0))

        Log.d(TAG, "distance1: $distance1")
        Log.d(TAG, "distance2: $distance2")
        Log.d(TAG, "distance3: $distance3")

        return inX && (distance1 < 14F.dp || distance2 < 14F.dp || distance3 < 14F.dp)
    }
}