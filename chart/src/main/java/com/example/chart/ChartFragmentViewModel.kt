package com.example.chart

import androidx.core.util.Consumer
import androidx.lifecycle.ViewModel
import com.example.core.di.scopes.FragmentScope
import com.example.core.interfaces.Router
import javax.inject.Inject

@FragmentScope
class ChartFragmentViewModel @Inject constructor(
    private val router: Router
) : ViewModel(), Consumer<ChartFragment.UiEvent> {

    override fun accept(event: ChartFragment.UiEvent) {
        when (event) {
            ChartFragment.UiEvent.BackClicked -> router.back()
        }
    }
}