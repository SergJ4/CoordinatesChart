package com.example.serg.coordinateschart

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.example.core.interfaces.INPUT_SCREEN
import com.example.core.interfaces.Router

class MainActivityViewModel(private val router: Router) : ViewModel() {

    fun handleNavigation(savedState: Bundle?) {
        if (savedState == null) {
            router.rootScreen(INPUT_SCREEN)
        }
    }
}