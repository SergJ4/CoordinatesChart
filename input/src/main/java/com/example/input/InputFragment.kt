package com.example.input

import androidx.fragment.app.Fragment

class InputFragment : Fragment() {

    sealed class UiEvent {
        data class LoadButtonClicked(val count: Int) : UiEvent()
    }

    internal data class UiModel(val isLoading: Boolean)
}