/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti.touch

import android.util.Log
import android.view.MotionEvent
import com.jdqm.graffiti.shape.Shape


/**
 * @FileName: Touch
 * @Description: 文件功能描述
 * @Author: Jdqm
 * @Date: 2022/5/27 15:41
 */
open class Touch(var shape: Shape) {
    private val TAG = "Touch"

    /**
     * 各子类实现具体的图形触发反馈
     */
    open fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                return down(event)
            }

            MotionEvent.ACTION_MOVE -> {
                return move(event)
            }

            MotionEvent.ACTION_UP ->{
                return up(event)
            }
        }
        return true
    }

    /**
     * 按下
     */
    open fun down(event: MotionEvent): Boolean {
        Log.d(TAG, "down: (${event.x},${event.y})")
        return true
    }

    /**
     * 移动
     */
    open fun move(event: MotionEvent): Boolean {
        Log.d(TAG, "move: (${event.x},${event.y})")
        return false
    }

    /**
     * 抬起
     */
    open fun up(event: MotionEvent): Boolean {
        Log.d(TAG, "up: (${event.x},${event.y})")
        return false
    }
}