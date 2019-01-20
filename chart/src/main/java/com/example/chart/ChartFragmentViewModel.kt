package com.example.chart

import androidx.core.util.Consumer
import androidx.lifecycle.ViewModel
import com.example.chart.domain.SaveBitmap
import com.example.core.di.scopes.FragmentScope
import com.example.core.extensions.SingleLiveEvent
import com.example.core.extensions.async
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@FragmentScope
class ChartFragmentViewModel @Inject constructor(
    private val router: Router,
    private val saveBitmap: SaveBitmap,
    private val strings: Strings,
    private val logger: Logger
) : ViewModel(), Consumer<ChartFragment.UiEvent> {

    private val compositeDisposable = CompositeDisposable()
    internal val news = SingleLiveEvent<String>()

    override fun accept(event: ChartFragment.UiEvent) {
        when (event) {
            ChartFragment.UiEvent.BackClicked -> router.back()
            is ChartFragment.UiEvent.SaveBitmapClicked -> {
                compositeDisposable.add(
                    saveBitmap(event.bitmap)
                        .async()
                        .subscribe(
                            {
                                news(strings.get(R.string.bitmap_save_success))
                            },
                            {
                                logger.logErrorIfDebug(it)
                                news(strings.get(R.string.bitmap_save_error))
                            }
                        )
                )
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}