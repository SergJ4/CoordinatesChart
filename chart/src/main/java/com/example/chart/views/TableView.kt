package com.example.chart.views

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.entity.Coordinate

class TableView : RecyclerView {

    private var adapter: TableViewAdapter? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    private fun init(context: Context) {
        layoutManager = LinearLayoutManager(context)
    }

    fun setData(coordinates: List<Coordinate>) {
        val items = mutableListOf(TableViewItem("X", "Y"))
        coordinates
            .asSequence()
            .map { TableViewItem(it.x.toString(), it.y.toString()) }
            .forEach { items.add(it) }

        if (adapter == null) {
            adapter = TableViewAdapter(items = items)
            this.setAdapter(adapter)
        } else {
            adapter!!.newData(items)
        }

    }
}