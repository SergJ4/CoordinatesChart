package com.example.input

import androidx.core.util.Consumer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.extensions.SingleLiveEvent
import com.example.core.extensions.async
import com.example.core.interfaces.Router
import com.example.input.domain.LoadCoordinates
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class InputFragmentViewModel @Inject constructor(
    private val router: Router,
    private val loadCoordinates: LoadCoordinates
) : ViewModel(), Consumer<InputFragment.UiEvent> {

    private val compositeDisposable = CompositeDisposable()

    internal val news = SingleLiveEvent<String>()
    internal val output = MutableLiveData<InputFragment.UiModel>()

    override fun accept(event: InputFragment.UiEvent) {
        when (event) {
            is InputFragment.UiEvent.LoadButtonClicked -> TODO()
        }
    }

    private fun newCountReceived(count: Int) {
        compositeDisposable.add(
            loadCoordinates(count)
                .async()
                .subscribe(
                    { coordinates ->
                        TODO()
                    },
                    { failure ->
                        TODO()
                    }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}