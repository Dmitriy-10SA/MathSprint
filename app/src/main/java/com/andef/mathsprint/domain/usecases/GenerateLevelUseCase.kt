package com.andef.mathsprint.domain.usecases

import com.andef.mathsprint.data.repository.GameRepositoryImpl
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty
import com.andef.mathsprint.domain.repository.GameRepository
import javax.inject.Inject

class GenerateLevelUseCase @Inject constructor(
    private val repository: GameRepository
){
    fun execute(levelDifficulty: LevelDifficulty): Game {
        return repository.generateLevel(levelDifficulty)
    }
}