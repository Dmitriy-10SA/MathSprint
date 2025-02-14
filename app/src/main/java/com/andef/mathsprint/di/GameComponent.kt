package com.andef.mathsprint.di

import android.app.Activity
import com.andef.mathsprint.presentation.activity.MainActivity
import com.andef.mathsprint.presentation.fragments.GameFragment
import dagger.Component

@ApplicationScope
@Component(modules = [GameRepositoryModule::class, ViewModelModule::class])
interface GameComponent {
    fun inject(gameFragment: GameFragment)
}