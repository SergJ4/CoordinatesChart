package com.example.chart

import android.content.Context
import android.util.AttributeSet
import android.widget.TableLayout
import com.example.core.entity.Coordinate

class TableView : TableLayout {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        //TODO отрисовать шапку
    }

    fun setData(coordinates: List<Coordinate>) {
        //TODO добавить строки в таблицу
    }
}