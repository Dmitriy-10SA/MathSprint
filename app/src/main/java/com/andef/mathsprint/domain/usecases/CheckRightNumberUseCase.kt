package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.data.repository.GameRepositoryImpl

object CheckRightNumberUseCase {
    private val repository = GameRepositoryImpl

    fun execute(rightNumber: Int): Boolean {
        return repository.checkRightNumber(rightNumber)
    }
}