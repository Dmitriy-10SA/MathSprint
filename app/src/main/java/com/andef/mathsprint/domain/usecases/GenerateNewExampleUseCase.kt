package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.data.repository.GameRepositoryImpl
import com.andef.mathsprint.domain.entities.Example

object GenerateNewExampleUseCase {
    private val repository = GameRepositoryImpl

    fun execute(): Example {
        return repository.generateNewExample()
    }
}