/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti.touch

import android.graphics.PointF
import android.util.Log
import android.view.MotionEvent
import com.jdqm.graffiti.dp
import com.jdqm.graffiti.shape.Shape
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * @FileName: TransformTouch
 * @Description: 平移
 * @Author: Jdqm
 * @Date: 2022/6/17 21:20
 */
class TransformTouch(shape: Shape) : Touch(shape){
    private val TAG = "TransformTouch"
    private var lastDownX: Float = 0F
    private var lastDownY: Float = 0F

    var select: Boolean = false

    override fun down(event: MotionEvent): Boolean {
        lastDownX = event.x
        lastDownY = event.y
        for (point in shape.points) {
            if (inCircle(event.x, event.y, 14F.dp, point)) {
                Log.d(TAG, "在圆里面")
                shape.curPoint = point
                shape.isActive = true
                return true
            }
        }


        if (shape.checkInPath(event.x, event.y)) {
            shape.isActive = true
            select = true
        } else {
            shape.isActive = false
        }
        return true
    }

    override fun move(event: MotionEvent): Boolean {
        val distanceX = event.x - lastDownX
        val distanceY = event.y - lastDownY

        if (shape.curPoint != null) {
            shape.curPoint!!.x += distanceX
            shape.curPoint!!.y += distanceY
        } else if (select) {
            shape.points.forEach {
                it.x += distanceX
                it.y += distanceY
            }
        }

        shape.updatePath()
        lastDownX = event.x
        lastDownY = event.y
        return true
    }

    override fun up(event: MotionEvent): Boolean {
        shape.curPoint = null
        select = false
        return true
    }

    private fun inCircle(x: Float, y: Float, r: Float, point: PointF): Boolean {
        val dx = x - point.x
        val dy = y - point.y
        return sqrt(dx.toDouble().pow(2.0) + dy.toDouble().pow(2.0)) < r
    }
}