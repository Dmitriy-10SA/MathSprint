package com.andef.mathsprint.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameSettings(
    val levelDifficulty: LevelDifficulty,
    val maxSumValue: Int,
    val minCntOfCorrectAnswers: Int,
    val minPercentOfCorrectAnswers: Int,
    val gameTimeInSeconds: Int
) : Parcelable