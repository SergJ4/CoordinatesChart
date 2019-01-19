package com.example.chart

import android.os.Bundle
import com.example.core.base.BaseFragment
import com.example.core.entity.Coordinate

private const val COORDINATES_LIST = "coordinates"

class ChartFragment : BaseFragment() {
    override val layout: Int
        get() = R.layout.chart_layout

    override fun setupViews() {
        //TODO
    }

    companion object {
        fun getInstance(coordinates: List<Coordinate>): ChartFragment {
            val fragment = ChartFragment()
            val args = Bundle()
            val newList = ArrayList<Coordinate>(coordinates)
            args.putSerializable(COORDINATES_LIST, newList)
            fragment.arguments = args
            return fragment
        }
    }
}