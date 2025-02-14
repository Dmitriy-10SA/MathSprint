package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.domain.repository.GameRepository
import javax.inject.Inject

class CheckRightNumberUseCase @Inject constructor(
    private val repository: GameRepository
) {
    fun execute(rightNumber: Int): Boolean {
        return repository.checkRightNumber(rightNumber)
    }
}