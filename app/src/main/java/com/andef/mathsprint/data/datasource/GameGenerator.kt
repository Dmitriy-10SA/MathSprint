package com.andef.mathsprint.data.datasource

import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.GameSettings
import com.andef.mathsprint.domain.entities.LevelDifficulty

object GameGenerator {
    fun generate(levelDifficulty: LevelDifficulty): Game {
        val gameSettings = getGameSettings(levelDifficulty)
        return Game(gameSettings, 0, 0, false)
    }

    private fun getGameSettings(levelDifficulty: LevelDifficulty): GameSettings {
        return when (levelDifficulty) {
            LevelDifficulty.EASY -> {
                GameSettings(
                    LevelDifficulty.EASY,
                    10,
                    20,
                    70,
                    70
                )
            }
            LevelDifficulty.MIDDLE -> {
                GameSettings(
                    LevelDifficulty.MIDDLE,
                    25,
                    25,
                    80,
                    60
                )
            }
            LevelDifficulty.HARD -> {
                GameSettings(
                    LevelDifficulty.HARD,
                    45,
                    25,
                    90,
                    60
                )
            }
        }
    }
}