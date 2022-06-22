/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jdqm.graffiti.shape.RectilinearLine
import com.jdqm.graffiti.shape.Shape
import com.jdqm.graffiti.touch.Touch
import com.jdqm.graffiti.touch.TransformTouch


/**
 * @FileName: DrawPanel
 * @Description: 画板
 * @Author: Jdqm
 * @Date: 2022/5/27 15:31
 */
class DrawPanel(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var drawStatus: DrawStatus = DrawStatus.NORMAL

    /**
     * 所有图形
     */
    private val shapeList = mutableListOf<Shape>()

    /**
     * 选中图形
     */
    private var selectShape: Shape? = null

    /**
     * 当前正在创建的图形
     */

    private var currentShape: Shape? = null


    /**
     * 当前触摸事件处理类，每个图形对应一个触摸事件处理类
     */
    private var touchTarget: Touch? = null

    private val transformTouch: TransformTouch = TransformTouch(RectilinearLine())

    fun updateCurrentTouch(touch: Touch) {
        touchTarget = touch
        touch.shape.drawPanel = this
        shapeList.add(touch.shape)
        drawStatus = DrawStatus.DRAWING
    }

    fun updateDrawStatus(status: DrawStatus) {
        drawStatus = status
        when(drawStatus) {
            DrawStatus.ACTIVE -> {
                currentShape = shapeList.last()
                transformTouch.shape = currentShape!!
                touchTarget = transformTouch
            }
            DrawStatus.DRAWING -> {

            }
            DrawStatus.NORMAL -> {

            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        var consume = false
        when(drawStatus) {
            DrawStatus.ACTIVE -> {
                consume = touchTarget?.onTouchEvent(event) ?: super.onTouchEvent(event)
                if (consume) {
                    invalidate()
                }
            }
            DrawStatus.DRAWING -> {
                consume = touchTarget?.onTouchEvent(event) ?: super.onTouchEvent(event)
                if (consume) {
                    invalidate()
                }
            }
            DrawStatus.NORMAL -> {
                when(event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        for(shape in shapeList) {
                            if (shape.checkInPath(event.x, event.y)) {
                                currentShape = shape
                                drawStatus = DrawStatus.ACTIVE
                                return true
                            }
                        }
                    }

                    MotionEvent.ACTION_UP ->{

                    }
                }
            }
        }

        return consume
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        shapeList.forEach {
            it.draw(canvas)
        }
    }
}