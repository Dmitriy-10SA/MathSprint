package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.data.repository.GameRepositoryImpl
import com.andef.mathsprint.domain.entities.Example
import com.andef.mathsprint.domain.repository.GameRepository
import javax.inject.Inject

class GenerateNewExampleUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(): Example {
        return repository.generateNewExample()
    }
}