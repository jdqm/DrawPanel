/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti


/**
 * @FileName: DrawStatus
 * @Description: 文件功能描述
 * @Author: Jdqm
 * @Date: 2022/6/19 16:26
 */
sealed class DrawStatus {
    object DRAWING: DrawStatus()
    object ACTIVE: DrawStatus()
    object NORMAL: DrawStatus()
}