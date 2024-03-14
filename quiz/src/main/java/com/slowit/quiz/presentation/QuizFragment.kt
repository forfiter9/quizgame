package com.slowit.quiz.presentation

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.map
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.slowit.core.extensions.showMessage
import com.slowit.quiz.R
import com.slowit.quiz.databinding.FragmentQuizBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {
    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by viewModels()
    private lateinit var choiceAdapter: ChoicesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        configureView()
        viewModel.getQuestions()

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.state
            .map { it.currentQuestion }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { currentQuestion ->
                currentQuestion?.let {
                    choiceAdapter.submitList(currentQuestion.choices)

                    Glide.with(requireContext())
                        .load(currentQuestion.image)
                        .into(binding.questionImage)

                    binding.questionText.text =
                        Html.fromHtml(currentQuestion.question, Html.FROM_HTML_MODE_COMPACT)

                    binding.linearProgressBar.apply {
                        isVisible = true
                        setProgress(100, true)
                    }

                    viewModel.startCountDownTimer(currentQuestion.time)
                }
            }

        viewModel.state
            .map { it.isLoadingQuestions }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { isLoading ->
                with(binding) {
                    questionText.isVisible = !isLoading
                    questionImage.isVisible = !isLoading
                    questionTextBackground.isVisible = !isLoading
                    choicesRecyclerView.isVisible = !isLoading
                    titleText.isVisible = !isLoading
                    tittleBackground.isVisible = !isLoading
                    tittleImage.isVisible = !isLoading
                    loadingProgress.isVisible = isLoading
                }
            }

        viewModel.state
            .map { it.selectedChoice }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { choice ->
                choiceAdapter.setSelectedChoice(choice)
                when {
                    choice == null -> {
                        binding.resultBanner.root.isVisible = false
                        binding.continueButton.isVisible = false
                    }

                    choice.isCorrectChoice -> {
                        with(binding.resultBanner) {
                            root.apply {
                                isVisible = true
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        com.slowit.core.R.color.green_ryb,
                                    ),
                                )
                            }
                            resultBannerText.setText(R.string.correct)
                        }
                        binding.continueButton.isVisible = true
                        binding.linearProgressBar.isVisible = false
                    }

                    !choice.isCorrectChoice -> {
                        with(binding.resultBanner) {
                            root.apply {
                                isVisible = true
                                setBackgroundColor(
                                    ContextCompat.getColor(
                                        requireContext(),
                                        com.slowit.core.R.color.sizzling_red,
                                    ),
                                )
                            }
                            resultBannerText.setText(R.string.wrong)
                        }
                        binding.continueButton.isVisible = true
                        binding.linearProgressBar.isVisible = false
                    }
                }
            }

        viewModel.state
            .map { it.isQuizFinished }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { isQuizFinished ->
                with(binding) {
                    if (isQuizFinished) {
                        questionText.text =
                            getString(
                                R.string.score_message,
                                viewModel.state.value?.score.toString(),
                            )
                        continueButton.isVisible = false
                        linearProgressBar.isVisible = false
                    }
                    questionImage.isVisible = !isQuizFinished
                    choicesRecyclerView.isVisible = !isQuizFinished
                    titleText.isVisible = !isQuizFinished
                    tittleBackground.isVisible = !isQuizFinished
                    tittleImage.isVisible = !isQuizFinished
                    counter.root.isVisible = !isQuizFinished
                }
            }

        viewModel.state
            .map { it.currentQuestionIndex }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { currentQuestionIndex ->
                binding.counter.counterText.text =
                    getString(
                        R.string.counter,
                        (currentQuestionIndex + 1).toString(),
                        viewModel.state.value?.questions?.size.toString(),
                    )
            }

        viewModel.state
            .map { it.isError }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) {
                if (it) {
                    context?.showMessage(getString(R.string.error_global_msg))
                }
            }

        viewModel.state
            .map { it.remainingTimePercentage }
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) {
                binding.linearProgressBar.setProgress(it, true)
            }
    }

    private fun configureView() {
        choiceAdapter =
            ChoicesAdapter().apply {
                onChoiceClickListener =
                    ChoicesAdapter.OnChoiceClickListener {
                        if (viewModel.state.value?.selectedChoice == null) {
                            viewModel.selectChoice(it)
                        }
                    }
            }

        with(binding) {
            choicesRecyclerView.apply {
                layoutManager = GridLayoutManager(context, SPAN_COUNT)
                adapter = choiceAdapter
                itemAnimator = null
            }
            continueButton.setOnClickListener { viewModel.showNextQuestion() }
        }
    }

    companion object {
        const val SPAN_COUNT = 2
    }
}
