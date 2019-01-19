package com.example.chart.views

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import com.example.core.entity.Coordinate

class ChartView : View {

    private val coordinates = ArrayList<Coordinate>()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    private fun init() {
        //TODO
    }

    fun setData(coordinates: List<Coordinate>) {
        this.coordinates.clear()
        coordinates
            .forEach { this.coordinates.add(it) }

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //TODO задать стандартную высоту
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //TODO отрисовать график
    }
}