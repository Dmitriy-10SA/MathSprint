package com.andef.mathsprint.presentation.app

import android.app.Application
import com.andef.mathsprint.di.DaggerGameComponent

class GameApplication: Application() {
    val component by lazy {
        DaggerGameComponent.create()
    }
}