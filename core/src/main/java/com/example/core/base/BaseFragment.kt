package com.example.core.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.core.R
import com.example.core.interfaces.Colors
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import org.jetbrains.anko.find
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    @Inject
    internal lateinit var colors: Colors

    protected abstract val layout: Int

    protected abstract fun setupViews()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layout, container, false)

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    protected fun showSnackbar(message: String) {
        val snackbar = Snackbar.make(
            view!!,
            message,
            Snackbar.LENGTH_LONG
        )
        val text = snackbar.view.find<TextView>(com.google.android.material.R.id.snackbar_text)
        text.setTextColor(colors.get(R.color.snack_bar_text))
        text.textSize = 14f
        snackbar.show()
    }
}