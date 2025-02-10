package com.andef.mathsprint.domain.entities

data class Example(
    val correctAnswer: Int,
    val leftNumber: Int,
    val rightNumber: Int,
    val variants: List<Int>
)
