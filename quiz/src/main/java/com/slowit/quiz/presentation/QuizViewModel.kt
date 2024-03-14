package com.slowit.quiz.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowit.quiz.domain.model.Choice
import com.slowit.quiz.domain.usecase.GetQuizQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel
    @Inject
    constructor(
        private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
    ) : ViewModel() {
        private val _state: MutableLiveData<QuizState> = MutableLiveData(QuizState.EMPTY)
        val state: LiveData<QuizState> = _state

        private var countDownTimer: CountDownTimer? = null

        fun getQuestions() {
            viewModelScope.launch {
                val questions = getQuizQuestionsUseCase.invoke()
                _state.value =
                    _state.value?.copy(
                        questions = questions,
                        isLoadingQuestions = false,
                        currentQuestionIndex = 0,
                        currentQuestion = questions.firstOrNull(),
                        isError = questions.isEmpty(),
                    )
            }
        }

        fun selectChoice(choice: Choice?) {
            countDownTimer?.cancel()
            _state.value?.let {
                _state.value =
                    _state.value?.copy(
                        selectedChoice = choice,
                        score = if (choice?.isCorrectChoice == true) it.score + 1 else it.score,
                    )
            }
        }

        fun showNextQuestion() {
            selectChoice(null)
            countDownTimer?.cancel()
            _state.value?.let {
                if (it.questions.size <= it.currentQuestionIndex + 1) {
                    _state.value =
                        _state.value?.copy(
                            isQuizFinished = true,
                        )
                } else {
                    _state.value =
                        _state.value?.copy(
                            currentQuestion = it.questions[it.currentQuestionIndex + 1],
                            currentQuestionIndex = it.currentQuestionIndex + 1,
                        )
                }
            }
        }

        fun startCountDownTimer(questionTime: Long) {
            val timerTickTime = 1000L
            countDownTimer =
                object : CountDownTimer(questionTime, timerTickTime) {
                    override fun onTick(millisUntilFinished: Long) {
                        _state.value =
                            _state.value?.copy(
                                remainingTimePercentage = ((millisUntilFinished * 100) / questionTime).toInt(),
                            )
                    }

                    override fun onFinish() {
                        showNextQuestion()
                    }
                }.start()
        }
    }
