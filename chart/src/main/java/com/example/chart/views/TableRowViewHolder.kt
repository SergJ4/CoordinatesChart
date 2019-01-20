package com.example.chart.views

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.chart.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.row_layout.*

class TableRowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun bind(item: TableViewItem, position: Int) {
        firstText.text = item.leftText
        secondText.text = item.rightText

        if (position % 2 == 0) {
            rowLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.table_row_even_background))
        } else {
            rowLayout.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.table_row_odd_background))
        }
    }
}