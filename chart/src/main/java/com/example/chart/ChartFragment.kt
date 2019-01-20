package com.example.chart

import android.os.Bundle
import com.example.core.base.BaseFragment
import com.example.core.entity.Coordinate
import kotlinx.android.synthetic.main.chart_layout.*
import javax.inject.Inject

private const val COORDINATES_LIST = "coordinates"

class ChartFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModel: ChartFragmentViewModel

    override val layout: Int
        get() = R.layout.chart_layout

    override fun setupViews() {
        val coordinates = arguments!![COORDINATES_LIST] as List<Coordinate>
        table.setData(coordinates)
        chart.setData(coordinates)
        backButton.setOnClickListener { viewModel.accept(UiEvent.BackClicked) }
    }

    sealed class UiEvent {
        object BackClicked : UiEvent()
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