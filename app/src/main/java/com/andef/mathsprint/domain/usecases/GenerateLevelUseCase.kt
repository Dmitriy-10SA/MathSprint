package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.data.repository.GameRepositoryImpl
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty

object GenerateLevelUseCase {
    private val repository = GameRepositoryImpl

    fun execute(levelDifficulty: LevelDifficulty): Game {
        return repository.generateLevel(levelDifficulty)
    }
}