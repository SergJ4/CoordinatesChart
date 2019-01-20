package com.example.chart.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.chart.R
import com.example.core.entity.Coordinate
import org.jetbrains.anko.dimen
import kotlin.math.abs

class ChartView : View {

    private val DEFAULT_POINT_RADIUS = 4f

    private val coordinates = ArrayList<Coordinate>()
    private val points = ArrayList<PointF>()

    private val linePaint = Paint()
    private val pointPaint = Paint()

    private var xStep = 0f
    private var yStep = 0f
    private var minX = 0f
    private var minY = 0f

    private var xAxisYCoordinate = 0f
    private var yAxisXCoordinate = 0f

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context)
    }

    private fun init(context: Context) {

        val lineColor: Int
        val lineWidth: Float
        val pointColor: Int

        if (isInEditMode) {
            lineColor = ContextCompat.getColor(context, android.R.color.black)
            lineWidth = 2f
            pointColor = ContextCompat.getColor(context, android.R.color.holo_blue_dark)
        } else {
            lineColor = ContextCompat.getColor(context, R.color.chart_line_color)
            lineWidth = context.dimen(R.dimen.chart_line_width).toFloat()
            pointColor = ContextCompat.getColor(context, R.color.chart_point_color)
        }

        linePaint.color = lineColor
        linePaint.strokeWidth = lineWidth
        linePaint.style = Paint.Style.STROKE

        pointPaint.color = pointColor
        pointPaint.style = Paint.Style.FILL
    }

    fun setData(coordinates: List<Coordinate>) {
        this.coordinates.clear()

        if (coordinates.isNotEmpty()) {
            coordinates
                .asSequence()
                .sortedBy { it.x }
                .forEach { this.coordinates.add(it) }

            calculateAllParams()
        }
        invalidate()
    }

    private fun calculateAllParams() {
        if (width == 0 || height == 0) {
            return
        }
        minX = coordinates.minBy { it.x }!!.x
        minY = coordinates.minBy { it.y }!!.y
        val coordinatesXRange = coordinates.maxBy { it.x }!!.x - minX
        val coordinatesYRange = coordinates.maxBy { it.y }!!.y - minY
        xStep = coordinatesXRange / width
        yStep = coordinatesYRange / height

        xAxisYCoordinate = height - (abs(minY) * yStep)
        yAxisXCoordinate = abs(minX) * xStep

        coordinates
            .forEach {
                var cx = ((it.x - minX) / xStep)
                if (cx < DEFAULT_POINT_RADIUS) {
                    cx = DEFAULT_POINT_RADIUS
                } else if (cx > width - DEFAULT_POINT_RADIUS) {
                    cx = width - DEFAULT_POINT_RADIUS
                }

                var cy = height - ((it.y - minY) / yStep)
                if (cy < DEFAULT_POINT_RADIUS) {
                    cy = DEFAULT_POINT_RADIUS
                } else if (cy > height - DEFAULT_POINT_RADIUS) {
                    cy = height - DEFAULT_POINT_RADIUS
                }

                points.add(PointF(cx, cy))
            }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateAllParams()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAxis(canvas)
        drawData(canvas)
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(0f, xAxisYCoordinate, width.toFloat(), xAxisYCoordinate, linePaint)
        canvas.drawLine(yAxisXCoordinate, 0f, yAxisXCoordinate, height.toFloat(), linePaint)
    }

    private fun drawData(canvas: Canvas) {
        points
            .forEach {
                canvas.drawCircle(
                    it.x,
                    it.y,
                    DEFAULT_POINT_RADIUS,
                    pointPaint
                )
            }
    }
}