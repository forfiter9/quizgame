package com.slowit.quiz.data.repositories

import android.util.Log
import com.slowit.quiz.data.mapper.toDomainModel
import com.slowit.quiz.data.remote.api.QuizQuestionApi
import com.slowit.quiz.domain.model.QuizQuestion
import com.slowit.quiz.domain.repository.QuizQuestionsRepository
import javax.inject.Inject

class QuizQuestionRepositoryImpl
    @Inject
    constructor(
        private val quizQuestionApi: QuizQuestionApi,
    ) : QuizQuestionsRepository {
        override suspend fun getQuizQuestions(): List<QuizQuestion> {
            var questionsList = emptyList<QuizQuestion>()
            runCatching {
                questionsList = quizQuestionApi.getQuizQuestions().toDomainModel()
            }.onFailure {
                Log.e("Quiz question download error", "${it.message}")
            }
            return questionsList
        }
    }
