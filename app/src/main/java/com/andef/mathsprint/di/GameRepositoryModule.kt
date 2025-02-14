package com.andef.mathsprint.di

import com.andef.mathsprint.data.repository.GameRepositoryImpl
import com.andef.mathsprint.domain.repository.GameRepository
import dagger.Binds
import dagger.Module

@Module
interface GameRepositoryModule {
    @Binds
    fun bindGameRepository(impl: GameRepositoryImpl): GameRepository
}