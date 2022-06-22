/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti.touch

import android.graphics.PointF
import android.view.MotionEvent
import com.jdqm.graffiti.DrawStatus
import com.jdqm.graffiti.shape.Shape


/**
 * @FileName: RectilinearLineTouch
 * @Description: 文件功能描述
 * @Author: Jdqm
 * @Date: 2022/5/27 15:57
 */
class TriangleTouch(shape: Shape) : Touch(shape) {

    override fun down(event: MotionEvent): Boolean {
        if (!shape.isDrawDone()) {
            shape.addPoint(PointF(event.x, event.y))
            return true
        }
        return false
    }

    override fun move(event: MotionEvent): Boolean {
        return true
    }

    override fun up(event: MotionEvent): Boolean {
        if (shape.isDrawDone()) {
            shape.drawPanel?.updateDrawStatus(DrawStatus.ACTIVE)
        }
        return true
    }
}