package com.andef.mathsprint.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty
import com.andef.mathsprint.domain.usecases.CheckRightNumberUseCase
import com.andef.mathsprint.domain.usecases.GenerateLevelUseCase
import com.andef.mathsprint.domain.usecases.GenerateNewExampleUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class GameViewModel : ViewModel() {
    private val checkRightNumber = CheckRightNumberUseCase
    private val generateLevel = GenerateLevelUseCase
    private val generateNewExample = GenerateNewExampleUseCase

    private val _rectangleFirst = MutableLiveData<Int>()
    val rectangleFirst: LiveData<Int> = _rectangleFirst

    private val _rectangleSecond = MutableLiveData<Int>()
    val rectangleSecond: LiveData<Int> = _rectangleSecond

    private val _rectangleThird = MutableLiveData<Int>()
    val rectangleThird: LiveData<Int> = _rectangleThird

    private val _rectangleFourth = MutableLiveData<Int>()
    val rectangleFourth: LiveData<Int> = _rectangleFourth

    private val _rectangleFifth = MutableLiveData<Int>()
    val rectangleFifth: LiveData<Int> = _rectangleFifth

    private val _rectangleSixth = MutableLiveData<Int>()
    val rectangleSixth: LiveData<Int> = _rectangleSixth

    private val _leftNumber = MutableLiveData<Int>()
    val leftNumber: LiveData<Int> = _leftNumber

    private val _answerNumber = MutableLiveData<Int>()
    val answerNumber: LiveData<Int> = _answerNumber

    private val _timer = MutableLiveData<Int>()
    val timer: LiveData<Int> = _timer

    private val _isTimeUp = MutableLiveData<Game>()
    val isTimeUp: LiveData<Game> = _isTimeUp

    private val _isCorrectAnswer = MutableLiveData<Boolean>()
    val isCorrectAnswer: LiveData<Boolean> = _isCorrectAnswer

    private val _correctAnswersCount = MutableLiveData<Pair<Int, Int>>()
    val correctAnswersCount: LiveData<Pair<Int, Int>> = _correctAnswersCount

    private val _accuracy = MutableLiveData<Pair<Int, Int>>()
    val accuracy: LiveData<Pair<Int, Int>> = _accuracy

    private val _minAccuracy = MutableLiveData<Int>()
    val minAccuracy: LiveData<Int> = _minAccuracy

    private lateinit var game: Game

    fun startGame(levelDifficulty: LevelDifficulty) {
        game = generateLevel.execute(levelDifficulty)
        _minAccuracy.value = game.gameSettings.minPercentOfCorrectAnswers
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            var seconds = game.gameSettings.gameTimeInSeconds
            while (seconds > 0) {
                _timer.value = seconds--
                delay(1000)
            }
            _isTimeUp.value = game
        }
    }

    fun checkNumber(rightNumber: Int) {
        _isCorrectAnswer.value = checkRightNumber.execute(rightNumber)
        _correctAnswersCount.value = Pair(
            game.correctAnswers,
            game.gameSettings.minCntOfCorrectAnswers
        )
        _accuracy.value = Pair(
            ((game.correctAnswers.toFloat() / game.totalAnswers) * 100).toInt(),
            game.gameSettings.minPercentOfCorrectAnswers
        )
    }

    fun newExample() {
        val example = generateNewExample.execute()
        _leftNumber.value = example.leftNumber
        _answerNumber.value = example.correctAnswer

        val variants = example.variants
        _rectangleFirst.value = variants[0]
        _rectangleSecond.value = variants[1]
        _rectangleThird.value = variants[2]
        _rectangleFourth.value = variants[3]
        _rectangleFifth.value = variants[4]
        _rectangleSixth.value = variants[5]
    }
}