package com.andef.mathsprint.presentation.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.andef.mathsprint.R
import com.andef.mathsprint.databinding.FragmentResultsBinding
import com.andef.mathsprint.domain.entities.Game
import com.andef.mathsprint.domain.entities.LevelDifficulty

class ResultsFragment : Fragment() {
    private lateinit var settings: SharedPreferences
    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var gameResult: Game

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameResult = ResultsFragmentArgs.fromBundle(requireArguments()).gameResult

        settings = requireActivity().getSharedPreferences(RECORDS, Context.MODE_PRIVATE)

        checkRecordsAndInitTextViewRecord()

        initRetryButtonAndBackPressed()

        initViews()
    }

    @SuppressLint("SetTextI18n")
    private fun checkRecordsAndInitTextViewRecord() {
        val editor = settings.edit()
        val levelDifficulty = gameResult.gameSettings.levelDifficulty
        val correctAnswers = gameResult.correctAnswers
        val percent = accuracyPercent()
        val recordEasy = settings.getInt(EASY_RECORD, 0)
        val recordMiddle = settings.getInt(MIDDLE_RECORD, 0)
        val recordHard = settings.getInt(HARD_RECORD, 0)
        val recordEasyPercent = settings.getInt(EASY_PERCENT_RECORD, 0)
        val recordMiddlePercent = settings.getInt(MIDDLE_PERCENT_RECORD, 0)
        val recordHardPercent = settings.getInt(HARD_PERCENT_RECORD, 0)
        var record: Int
        var recordPercent: Int
        when (levelDifficulty) {
            LevelDifficulty.EASY -> {
                record = recordEasy
                recordPercent = recordEasyPercent
                if (recordEasy < correctAnswers && percent >= recordEasyPercent) {
                    editor.putInt(EASY_RECORD, correctAnswers)
                    editor.putInt(EASY_PERCENT_RECORD, percent)
                    record = correctAnswers
                    recordPercent = percent
                }
            }

            LevelDifficulty.MIDDLE -> {
                record = recordMiddle
                recordPercent = recordMiddlePercent
                if (recordMiddle < correctAnswers && percent >= recordMiddlePercent) {
                    editor.putInt(MIDDLE_RECORD, correctAnswers)
                    editor.putInt(MIDDLE_PERCENT_RECORD, percent)
                    record = correctAnswers
                    recordPercent = percent
                }
            }

            LevelDifficulty.HARD -> {
                record = recordHard
                recordPercent = recordHardPercent
                if (recordHard < correctAnswers && percent >= recordHardPercent) {
                    editor.putInt(HARD_RECORD, correctAnswers)
                    editor.putInt(HARD_PERCENT_RECORD, percent)
                    record = correctAnswers
                    recordPercent = percent
                }
            }
        }
        editor.apply()
        binding.textViewRecord.text = String.format(
            getString(R.string.record),
            record
        ) + " ($recordPercent%)"
    }

    private fun initRetryButtonAndBackPressed() {
        binding.buttonRestart.setOnClickListener {
            retry()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViews() {
        val resId = if (accuracyPercent() >= gameResult.gameSettings.minPercentOfCorrectAnswers
            && gameResult.correctAnswers >= gameResult.gameSettings.minCntOfCorrectAnswers
        ) {
            R.drawable.happy_smile_wink_emoji_emoticon_svgrepo_com
        } else {
            R.drawable.sad_emoji_emoticon_2_svgrepo_com
        }
        with(binding) {
            imageViewSmile.setImageResource(resId)
            textViewRequiredQuantity.text = String.format(
                getString(R.string.required_quantity),
                gameResult.gameSettings.minCntOfCorrectAnswers
            )
            textViewYourQuantity.text = String.format(
                getString(R.string.your_quantity),
                gameResult.correctAnswers
            )
            textViewRequiredQuantityPercent.text = String.format(
                getString(R.string.required_quantity_percent),
                gameResult.gameSettings.minPercentOfCorrectAnswers
            ) + "%"
            textViewYourPercent.text = String.format(
                getString(R.string.your_percent),
                accuracyPercent()
            ) + "%"
        }
    }

    private fun accuracyPercent(): Int {
        return ((gameResult.correctAnswers.toFloat() / gameResult.totalAnswers) * 100).toInt()
    }

    private fun retry() = findNavController().popBackStack()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val RECORDS = "records"
        private const val EASY_RECORD = "easyRecord"
        private const val MIDDLE_RECORD = "middleRecord"
        private const val HARD_RECORD = "hardRecord"
        private const val EASY_PERCENT_RECORD = "easyPercentRecord"
        private const val MIDDLE_PERCENT_RECORD = "middlePercentRecord"
        private const val HARD_PERCENT_RECORD = "hardPercentRecord"
    }
}