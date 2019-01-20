package com.example.chart.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chart.R

class TableViewAdapter(private var items: List<TableViewItem>) : RecyclerView.Adapter<TableRowViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return TableRowViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: TableRowViewHolder, position: Int) =
        holder.bind(items[position], position)

    fun newData(items: List<TableViewItem>) {
        this.items = items
        notifyDataSetChanged()
    }
}

data class TableViewItem(val leftText: String, val rightText: String)