package com.andef.mathsprint.presentation.fragments

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.andef.mathsprint.R
import com.andef.mathsprint.databinding.FragmentGameBinding
import com.andef.mathsprint.domain.entities.LevelDifficulty
import com.andef.mathsprint.presentation.app.GameApplication
import com.andef.mathsprint.presentation.factory.ViewModelFactory
import com.andef.mathsprint.presentation.viewmodel.GameViewModel
import javax.inject.Inject

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val component by lazy {
        (requireActivity().application as GameApplication).component
    }

    private lateinit var levelDifficulty: LevelDifficulty

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[GameViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        levelDifficulty = GameFragmentArgs.fromBundle(requireArguments()).levelDifficulty
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTextViews()
        initViewModel()
    }

    private fun initTextViews() {
        with(binding) {
            initTextViewRectangle(textViewRectangleSixth)
            initTextViewRectangle(textViewRectangleFifth)
            initTextViewRectangle(textViewRectangleFourth)
            initTextViewRectangle(textViewRectangleThird)
            initTextViewRectangle(textViewRectangleSecond)
            initTextViewRectangle(textViewRectangleFirst)
        }
    }

    private fun initTextViewRectangle(textViewRectangle: TextView) {
        textViewRectangle.setOnClickListener {
            viewModel.checkNumber(textViewRectangle.text.toString().toInt())
            viewModel.newExample()
        }
    }

    private fun initViewModel() {
        initViewModelObservers()

        viewModel.startGame(levelDifficulty)
        viewModel.newExample()
    }

    @SuppressLint("DefaultLocale")
    private fun initViewModelObservers() {
        viewModel.answerNumber.observe(viewLifecycleOwner) {
            binding.textViewCorrectNumber.text = "$it"
        }
        viewModel.leftNumber.observe(viewLifecycleOwner) {
            binding.textViewLeftNumber.text = "$it"
        }
        viewModel.rectangleFirst.observe(viewLifecycleOwner) {
            binding.textViewRectangleFirst.text = "$it"
        }
        viewModel.rectangleSecond.observe(viewLifecycleOwner) {
            binding.textViewRectangleSecond.text = "$it"
        }
        viewModel.rectangleThird.observe(viewLifecycleOwner) {
            binding.textViewRectangleThird.text = "$it"
        }
        viewModel.rectangleFourth.observe(viewLifecycleOwner) {
            binding.textViewRectangleFourth.text = "$it"
        }
        viewModel.rectangleFifth.observe(viewLifecycleOwner) {
            binding.textViewRectangleFifth.text = "$it"
        }
        viewModel.rectangleSixth.observe(viewLifecycleOwner) {
            binding.textViewRectangleSixth.text = "$it"
        }
        viewModel.timer.observe(viewLifecycleOwner) {
            binding.textViewTimer.text = String.format("%02d:%02d", it / 60, it % 60)
            if (it < 10) {
                binding.textViewTimer.setTextColor(Color.RED)
            }
        }
        viewModel.isTimeUp.observe(viewLifecycleOwner) { game ->
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToResultsFragment(game)
            )
        }
        viewModel.isCorrectAnswer.observe(viewLifecycleOwner) { isCorrectAnswer ->
            if (isCorrectAnswer) {
                binding.textViewRightAnswers.setTextColor(Color.BLACK)
            } else {
                binding.textViewRightAnswers.setTextColor(Color.RED)
            }
        }
        viewModel.correctAnswersCount.observe(viewLifecycleOwner) { countAndMin ->
            binding.textViewRightAnswers.text = String.format(
                getString(R.string.right_answers),
                countAndMin.first,
                countAndMin.second
            )
        }
        viewModel.accuracy.observe(viewLifecycleOwner) { accuracyAndMin ->
            with(binding.progressBarAccuracy) {
                progress = accuracyAndMin.first
                progressTintList = if (accuracyAndMin.first >= accuracyAndMin.second) {
                    ColorStateList.valueOf(Color.GREEN)
                } else {
                    ColorStateList.valueOf(Color.RED)
                }
            }
        }
        viewModel.minAccuracy.observe(viewLifecycleOwner) { minAccuracy ->
            binding.progressBarAccuracy.secondaryProgress = minAccuracy
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}