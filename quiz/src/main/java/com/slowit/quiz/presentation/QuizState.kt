package com.slowit.quiz.presentation

import com.slowit.quiz.domain.model.Choice
import com.slowit.quiz.domain.model.QuizQuestion

data class QuizState(
    val questions: List<QuizQuestion>,
    val currentQuestion: QuizQuestion?,
    val currentQuestionIndex: Int,
    val isLoadingQuestions: Boolean,
    val selectedChoice: Choice?,
    val score: Int,
    val isQuizFinished: Boolean,
    val isError: Boolean,
    val remainingTimePercentage: Int,
) {
    companion object {
        val EMPTY =
            QuizState(
                questions = emptyList(),
                currentQuestion = null,
                currentQuestionIndex = -1,
                isLoadingQuestions = false,
                selectedChoice = null,
                score = 0,
                isQuizFinished = false,
                isError = false,
                remainingTimePercentage = 0,
            )
    }
}
