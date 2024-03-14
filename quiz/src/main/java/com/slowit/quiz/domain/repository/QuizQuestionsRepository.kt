package com.slowit.quiz.domain.repository

import com.slowit.quiz.domain.model.QuizQuestion

interface QuizQuestionsRepository {
    suspend fun getQuizQuestions(): List<QuizQuestion>
}
