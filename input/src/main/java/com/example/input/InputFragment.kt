package com.example.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class InputFragment : Fragment() {

    sealed class UiEvent {
        data class LoadButtonClicked(val count: Int) : UiEvent()
    }

    internal data class UiModel(val isLoading: Boolean, val validationError: Boolean)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.input_fragment, container, false)
}