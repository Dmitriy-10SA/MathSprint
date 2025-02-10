package com.andef.mathsprint.data.repository

import com.andef.mathsprint.data.datasource.GameGenerator
import com.andef.mathsprint.domain.entities.Example
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty
import com.andef.mathsprint.domain.repository.GameRepository

object GameRepositoryImpl : GameRepository {
    private const val NUMBER_DEFAULT_INIT = -1
    private const val MIN_VALUE = 2

    private lateinit var game: Game
    private var lastRightNumber: Int = NUMBER_DEFAULT_INIT
    private var lastLeftNumber: Int = NUMBER_DEFAULT_INIT

    override fun checkRightNumber(rightNumber: Int): Boolean {
        var isCorrectNumber = false
        if (rightNumber == lastRightNumber) {
            game.correctAnswers++
            isCorrectNumber = true
        }
        game.totalAnswers++
        generateNewExample()
        return isCorrectNumber
    }

    override fun generateLevel(levelDifficulty: LevelDifficulty): Game {
        game = GameGenerator.generate(levelDifficulty)
        return game
    }

    override fun generateNewExample(): Example {
        val maxSumValue = game.gameSettings.maxSumValue

        var leftNumber = (MIN_VALUE until (maxSumValue - 1)).random()
        while (leftNumber == lastLeftNumber) {
            leftNumber = (MIN_VALUE until (maxSumValue - 1)).random()
        }
        val rightNumber = (MIN_VALUE..(maxSumValue - leftNumber)).random()
        val correctAnswer = leftNumber + rightNumber

        lastLeftNumber = leftNumber
        lastRightNumber = rightNumber

        val variants = mutableSetOf(rightNumber).apply {
            for (i in 0..4) {
                var randomNumber = (MIN_VALUE until maxSumValue).random()
                while (contains(randomNumber)) {
                    randomNumber = (MIN_VALUE until maxSumValue).random()
                }
                add(randomNumber)
            }
        }.shuffled().toList()
        return Example(correctAnswer, leftNumber, rightNumber, variants)
    }
}