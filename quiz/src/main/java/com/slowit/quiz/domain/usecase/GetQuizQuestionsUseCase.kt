package com.slowit.quiz.domain.usecase

import com.slowit.quiz.domain.model.QuizQuestion
import com.slowit.quiz.domain.repository.QuizQuestionsRepository
import javax.inject.Inject

class GetQuizQuestionsUseCase
    @Inject
    constructor(
        private val quizQuestionsRepository: QuizQuestionsRepository,
    ) {
        suspend fun invoke(): List<QuizQuestion> =
            quizQuestionsRepository
                .getQuizQuestions()
    }
