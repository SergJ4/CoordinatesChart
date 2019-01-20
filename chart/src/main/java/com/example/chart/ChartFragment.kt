package com.example.chart

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import com.example.core.base.BaseFragment
import com.example.core.entity.Coordinate
import com.example.core.extensions.subscribe
import kotlinx.android.synthetic.main.chart_layout.*
import javax.inject.Inject

private const val COORDINATES_LIST = "coordinates"
private const val PERMISSION_REQUEST = 999

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
        saveChartButton.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST)
        }

        viewModel
            .news
            .subscribe(viewLifecycleOwner) {
                showSnackbar(it!!)
                saveChartButton.isEnabled = true
            }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.accept(UiEvent.SaveBitmapClicked(chart.getBitmap()))
            saveChartButton.isEnabled = false
        }
    }
    sealed class UiEvent {
        object BackClicked : UiEvent()
        data class SaveBitmapClicked(val bitmap: Bitmap) : UiEvent()
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