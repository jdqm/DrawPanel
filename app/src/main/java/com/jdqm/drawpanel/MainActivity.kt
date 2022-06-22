package com.jdqm.drawpanel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jdqm.graffiti.DrawPanel
import com.jdqm.graffiti.shape.RectilinearLine
import com.jdqm.graffiti.shape.Triangle
import com.jdqm.graffiti.touch.RectilinearLineTouch
import com.jdqm.graffiti.touch.TriangleTouch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val drawPanel = findViewById<DrawPanel>(R.id.draw_panel)

//        drawPanel.updateCurrentTouch(RectilinearLineTouch(RectilinearLine()))
        drawPanel.updateCurrentTouch(TriangleTouch(Triangle()))
    }
}