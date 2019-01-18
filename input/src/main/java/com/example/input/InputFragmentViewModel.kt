package com.example.input

import androidx.core.util.Consumer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.entity.Coordinate
import com.example.core.exception.Failure
import com.example.core.extensions.SingleLiveEvent
import com.example.core.extensions.async
import com.example.core.extensions.invoke
import com.example.core.extensions.startWith
import com.example.core.interfaces.Logger
import com.example.core.interfaces.Router
import com.example.core.interfaces.Strings
import com.example.input.domain.LoadCoordinates
import com.example.input.domain.ValidationFailure
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class InputFragmentViewModel @Inject constructor(
    private val router: Router,
    private val loadCoordinates: LoadCoordinates,
    private val strings: Strings,
    private val logger: Logger
) : ViewModel(), Consumer<InputFragment.UiEvent> {

    private val compositeDisposable = CompositeDisposable()

    internal val news = SingleLiveEvent<String>()
    internal val output =
        MutableLiveData<InputFragment.UiModel>()
            .startWith(InputFragment.UiModel(false, false))

    override fun accept(event: InputFragment.UiEvent) {
        when (event) {
            is InputFragment.UiEvent.LoadButtonClicked -> {
                val newUiModel = output.value!!.copy(isLoading = true, validationError = false)
                output(newUiModel)
                newCountReceived(event.count)
            }
        }
    }

    private fun newCountReceived(count: Int) {
        compositeDisposable.add(
            loadCoordinates(count)
                .async()
                .subscribe(
                    { handleCoordinates(it) },
                    { handleFailure(it) }
                )
        )
    }

    private fun handleCoordinates(coordinates: List<Coordinate>) {
        //TODO здесь надо переходить на экран с таблицей и графиком и передать туда координаты
    }

    private fun handleFailure(exception: Throwable) {
        when (exception) {
            is Failure.NetworkConnection -> {
                news(strings.networkConnectionError())
                val newUiModel = output.value!!.copy(isLoading = false, validationError = false)
                output(newUiModel)
            }

            is Failure.ServerError -> {
                val message = if (!exception.message.isNullOrBlank()) {
                    exception.message
                } else if (exception.messageResource != null) {
                    strings.get(exception.messageResource!!)
                } else {
                    strings.unknownErrorString()
                }

                news(message)
                val newUiModel = output.value!!.copy(isLoading = false, validationError = false)
                output(newUiModel)
            }

            is ValidationFailure -> {
                val newUiModel = output.value!!.copy(isLoading = false, validationError = true)
                output(newUiModel)
            }

            else -> {
                logger.logErrorIfDebug(exception)
                news(strings.networkConnectionError())
                val newUiModel = output.value!!.copy(isLoading = false, validationError = false)
                output(newUiModel)
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}