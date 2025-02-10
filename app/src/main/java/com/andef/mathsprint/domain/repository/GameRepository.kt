package com.andef.mathsprint.domain.repository

import com.andef.mathsprint.domain.entities.Example
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty

interface GameRepository {
    fun checkRightNumber(rightNumber: Int): Boolean
    fun generateLevel(levelDifficulty: LevelDifficulty): Game
    fun generateNewExample(): Example
}