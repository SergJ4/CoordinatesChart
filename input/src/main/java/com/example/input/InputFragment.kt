package com.example.input

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.core.base.BaseFragment
import com.example.core.extensions.subscribe
import kotlinx.android.synthetic.main.input_fragment.*
import javax.inject.Inject

class InputFragment : BaseFragment() {

    @Inject
    internal lateinit var viewModel: InputFragmentViewModel

    override val layout: Int
        get() = R.layout.input_fragment

    override fun setupViews() {
        goButton.setOnClickListener {
            view!!.hideKeyboard()
            viewModel.accept(UiEvent.LoadButtonClicked(countInput.text.toString().toInt()))
        }

        viewModel
            .output
            .subscribe(viewLifecycleOwner) {
                progress.isRefreshing = it!!.isLoading
                goButton.isEnabled = !it.isLoading

                if (it.validationError) {
                    countInput.error = getString(R.string.validation_error)
                } else {
                    countInput.error = null
                }

                countInputLayout.isErrorEnabled = it.validationError
            }

        viewModel
            .news
            .subscribe(viewLifecycleOwner) { showSnackbar(it!!) }
    }

    sealed class UiEvent {
        data class LoadButtonClicked(val count: Int) : UiEvent()
    }

    internal data class UiModel(val isLoading: Boolean, val validationError: Boolean)

    companion object {
        fun getInstance() = InputFragment()
    }
}

private fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(windowToken, 0)
}