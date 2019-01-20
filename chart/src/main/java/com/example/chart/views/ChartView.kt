package com.example.chart.views

import android.content.Context
import android.graphics.*
import android.os.Build
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.chart.R
import com.example.core.entity.Coordinate
import org.jetbrains.anko.dimen
import kotlin.math.abs

class ChartView : View {

    private val DEFAULT_POINT_RADIUS = 6f
    private val SMOOTH_FACTOR = 0.15f

    private var pointRadius = DEFAULT_POINT_RADIUS
    private val coordinates = ArrayList<Coordinate>()
    private val points = ArrayList<PointF>()

    private val linePaint = Paint()
    private val axisPaint = Paint()
    private val pointPaint = Paint()
    private val textPaint = TextPaint()

    private var xStep = 0f
    private var yStep = 0f
    private var minX = 0f
    private var minY = 0f

    private var xLabelWidth = 0f
    private var xLabelHeight = 0f
    private var yLabelWidth = 0f
    private var yLabelHeight = 0f

    private var xLabelX = 0f
    private var xLabelY = 0f
    private var yLabelX = 0f
    private var yLabelY = 0f

    private var xAxisYCoordinate = 0f
    private var yAxisXCoordinate = 0f

    private var isSmooth = false
    private val path = Path()

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))

        val lineColor: Int
        var lineWidth: Float
        val pointColor: Int
        val textColor: Int
        val textSize: Float

        if (attrs != null) {
            val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.ChartView, 0, 0)
            lineColor = typedArray.getColor(
                R.styleable.ChartView_lineColor,
                ContextCompat.getColor(context, R.color.chart_line_color)
            )
            lineWidth = typedArray.getDimension(
                R.styleable.ChartView_lineWidth,
                context.dimen(R.dimen.chart_line_width).toFloat()
            )

            pointColor = typedArray.getColor(
                R.styleable.ChartView_dotColor,
                ContextCompat.getColor(context, R.color.chart_point_color)
            )

            pointRadius = typedArray.getDimension(
                R.styleable.ChartView_dotSize,
                DEFAULT_POINT_RADIUS
            )

            if (lineWidth > pointRadius) {
                lineWidth = pointRadius
            }

            isSmooth = typedArray.getBoolean(R.styleable.ChartView_isSmooth, false)

            typedArray.recycle()
        } else {
            lineColor = ContextCompat.getColor(context, R.color.chart_line_color)
            lineWidth = context.dimen(R.dimen.chart_line_width).toFloat()
            pointColor = ContextCompat.getColor(context, R.color.chart_point_color)
        }

        if (isInEditMode) {
            textColor = ContextCompat.getColor(context, android.R.color.black)
            textSize = 16f
        } else {
            textSize = context.dimen(R.dimen.chart_text_size).toFloat()
            textColor = ContextCompat.getColor(context, R.color.chart_text_color)
        }

        linePaint.color = lineColor
        linePaint.strokeWidth = lineWidth
        linePaint.style = Paint.Style.STROKE

        axisPaint.color = ContextCompat.getColor(context, android.R.color.black)
        axisPaint.strokeWidth = context.dimen(R.dimen.chart_line_width).toFloat()
        axisPaint.style = Paint.Style.STROKE

        pointPaint.color = pointColor
        pointPaint.style = Paint.Style.FILL

        textPaint.color = textColor
        textPaint.textSize = textSize

        xLabelWidth = textPaint.measureText("x")
        yLabelWidth = textPaint.measureText("y")

        val rect = Rect()
        textPaint.getTextBounds("x", 0, "x".length, rect)
        xLabelHeight = rect.height().toFloat()

        textPaint.getTextBounds("y", 0, "y".length, rect)
        yLabelHeight = rect.height().toFloat()
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
    }

    private fun calculateAllParams() {
        if (width == 0 || height == 0) {
            return
        }
        minX = coordinates.minBy { it.x }!!.x
        minY = coordinates.minBy { it.y }!!.y
        val coordinatesXRange = coordinates.maxBy { it.x }!!.x - minX
        val coordinatesYRange = coordinates.maxBy { it.y }!!.y - minY
        xStep = coordinatesXRange / (width - paddingLeft - paddingRight)
        yStep = coordinatesYRange / (height - paddingBottom - paddingTop)
        xAxisYCoordinate = if (minY < 0) {
            height.toFloat() - paddingBottom - (abs(minY) / yStep)
        } else {
            height.toFloat() - paddingBottom
        }

        yAxisXCoordinate = if (minX < 0) {
            abs(minX) / xStep + paddingLeft.toFloat()
        } else {
            paddingLeft.toFloat()
        }

        coordinates
            .forEach {
                var cx = ((it.x - minX) / xStep)
                if (cx < pointRadius + paddingLeft) {
                    cx = pointRadius + paddingLeft
                } else if (cx > width - pointRadius - paddingRight) {
                    cx = width - pointRadius - paddingRight
                }

                var cy = height - ((it.y - minY) / yStep)
                if (cy < pointRadius + paddingTop) {
                    cy = pointRadius + paddingTop
                } else if (cy > height - pointRadius - paddingTop) {
                    cy = height - pointRadius - paddingTop
                }

                points.add(PointF(cx, cy))
            }

        xLabelY = if (xAxisYCoordinate < xLabelHeight + 16) {
            xAxisYCoordinate + 8
        } else {
            xAxisYCoordinate - 8
        }
        xLabelX = width - xLabelWidth - paddingRight

        yLabelX = if (yAxisXCoordinate > yLabelWidth + 16) {
            yAxisXCoordinate - yLabelWidth - 8
        } else {
            yAxisXCoordinate + 8
        }
        yLabelY = yLabelHeight + 4 + paddingTop
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        calculateAllParams()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawAxis(canvas)

        if (isSmooth) {
            drawSmoothData(canvas)
        } else {
            drawData(canvas)
        }
    }

    private fun drawAxis(canvas: Canvas) {
        canvas.drawLine(
            paddingLeft.toFloat(),
            xAxisYCoordinate,
            width.toFloat() - paddingRight,
            xAxisYCoordinate,
            axisPaint
        )
        canvas.drawLine(
            yAxisXCoordinate,
            paddingTop.toFloat(),
            yAxisXCoordinate,
            height.toFloat() - paddingTop,
            axisPaint
        )

        canvas.drawText("x", xLabelX, xLabelY, textPaint)
        canvas.drawText("y", yLabelX, yLabelY, textPaint)
    }

    private fun drawSmoothData(canvas: Canvas) {
        for (i in 0 until points.size) {
            val currentPoint = points[i]
            val previousPoint = if (i > 0) {
                points[i - 1]
            } else {
                currentPoint
            }
            val prePreviousPoint = if (i > 1) {
                points[i - 2]
            } else {
                previousPoint
            }
            val nextPoint = if (i < points.size - 1) {
                points[i + 1]
            } else {
                currentPoint
            }

            if (i == 0) {
                path.moveTo(currentPoint.x, currentPoint.y)
            } else {
                val firstDiffX = currentPoint.x - prePreviousPoint.x
                val firstDiffY = currentPoint.y - prePreviousPoint.y
                val secondDiffX = nextPoint.x - previousPoint.x
                val secondDiffY = nextPoint.y - previousPoint.y
                val firstControlPointX = previousPoint.x + SMOOTH_FACTOR * firstDiffX
                val firstControlPointY = previousPoint.y + SMOOTH_FACTOR * firstDiffY
                val secondControlPointX = currentPoint.x - SMOOTH_FACTOR * secondDiffX
                val secondControlPointY = currentPoint.y - SMOOTH_FACTOR * secondDiffY
                path.cubicTo(
                    firstControlPointX, firstControlPointY, secondControlPointX, secondControlPointY,
                    currentPoint.x, currentPoint.y
                )
            }

            canvas.drawCircle(
                currentPoint.x,
                currentPoint.y,
                pointRadius,
                pointPaint
            )
        }

        canvas.drawPath(path, linePaint)
        path.reset()
    }

    private fun drawData(canvas: Canvas) {
        for (i in 0 until points.size - 1) {
            val firstPoint = points[i]
            val secondPoint = points[i + 1]

            if (i == 0) {
                canvas.drawCircle(
                    firstPoint.x,
                    firstPoint.y,
                    pointRadius,
                    pointPaint
                )
            }

            canvas.drawCircle(
                secondPoint.x,
                secondPoint.y,
                pointRadius,
                pointPaint
            )

            canvas.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y, linePaint)
        }
    }

    fun getBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        draw(canvas)
        return bitmap
    }
}