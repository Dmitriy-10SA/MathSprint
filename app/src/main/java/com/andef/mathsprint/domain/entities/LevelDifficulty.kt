package com.andef.mathsprint.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class LevelDifficulty : Parcelable {
    EASY, MIDDLE, HARD
}