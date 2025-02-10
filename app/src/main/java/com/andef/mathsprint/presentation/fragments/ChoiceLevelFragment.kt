package com.andef.mathsprint.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.andef.mathsprint.R
import com.andef.mathsprint.databinding.FragmentChoiceLevelBinding
import com.andef.mathsprint.domain.entities.LevelDifficulty


class ChoiceLevelFragment : Fragment() {
    private var _binding: FragmentChoiceLevelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingButtons()
    }

    private fun bindingButtons() {
        with(binding) {
            buttonEasyLevel.setOnClickListener {
                startGameFragment(LevelDifficulty.EASY)
            }
            buttonMiddleLevel.setOnClickListener {
                startGameFragment(LevelDifficulty.MIDDLE)
            }
            buttonHardLevel.setOnClickListener {
                startGameFragment(LevelDifficulty.HARD)
            }
        }
    }

    private fun startGameFragment(levelDifficulty: LevelDifficulty) {
        findNavController().navigate(
            ChoiceLevelFragmentDirections.actionChoiceLevelFragmentToGameFragment(levelDifficulty)
        )
    }
}