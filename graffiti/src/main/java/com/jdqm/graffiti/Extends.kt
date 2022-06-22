/*
 * Copyright 2022 uSMART, Ltd.
 *
 */

package com.jdqm.graffiti

import android.content.res.Resources
import android.util.TypedValue


/**
 * @FileName: Extends
 * @Description: 扩展方法
 * @Author: Jdqm
 * @Date: 2022/6/19 16:11
 */
val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )