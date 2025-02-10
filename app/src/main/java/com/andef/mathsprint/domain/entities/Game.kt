package com.andef.mathsprint.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val gameSettings: GameSettings,
    var correctAnswers: Int,
    var totalAnswers: Int,
    var isTimesUp: Boolean
) : Parcelable
