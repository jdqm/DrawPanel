/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti.shape

import android.graphics.*
import com.jdqm.graffiti.DrawPanel
import kotlin.math.max
import kotlin.math.min


/**
 * @FileName: Shape
 * @Description: 形状的基类
 * @Author: Jdqm
 * @Date: 2022/5/27 15:39
 */
open class Shape() {
    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 10f
        paint.color = Color.RED
        paint.strokeCap = Paint.Cap.ROUND
    }

    /**
     * 定位点集合
     */
    val points = mutableListOf<PointF>()

    /**
     * 当前选中的点
     */

    var curPoint: PointF? = null

    /**
     * 图形path
     */
    open var path: Path? = null

    /**
     * 是否处于激活（选中）状态
     */
    open var isActive = true

    var drawPanel: DrawPanel? = null

    /**
     * 各子类实现具体的图形绘制工作
     */
    open fun draw(canvas: Canvas) {

    }

    /**
     * 添加点
     */
    open fun addPoint(point: PointF) {
        points.add(point)
    }

    /**
     * 更新path
     */
    open fun updatePath() {

    }

    /**
     * 是否已完成图形构建
     */
    open fun isDrawDone(): Boolean {
        return false
    }

    /**
     * 检测点是否在Path上
     */
    open fun checkInPath(x: Float, y: Float): Boolean {
        var leftX = Float.MAX_VALUE
        var rightX = Float.MIN_VALUE
        var topY = Float.MAX_VALUE
        var bottomY = Float.MIN_VALUE

        points.forEach {
            leftX = min(it.x, leftX)
            rightX = max(it.x, rightX)
            topY = min(it.y, topY)
            bottomY = max(it.y, bottomY)
        }
        val inX = x > leftX && x < rightX
        val inY = y > topY && y < bottomY
        return inX && inY
    }
}