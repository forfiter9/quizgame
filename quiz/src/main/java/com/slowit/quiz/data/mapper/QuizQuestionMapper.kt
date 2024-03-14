package com.slowit.quiz.data.mapper

import com.slowit.quiz.data.remote.model.QuestionResponse
import com.slowit.quiz.data.remote.model.QuizResponse
import com.slowit.quiz.domain.model.Choice
import com.slowit.quiz.domain.model.QuizQuestion

fun QuizResponse.toDomainModel() = this.questions.toDomainModel()

fun List<QuestionResponse>.toDomainModel() =
    this.map { questionResponse ->
        QuizQuestion(
            question = questionResponse.question,
            time = questionResponse.time,
            image = questionResponse.image,
            choices =
                questionResponse.choices.map {
                    Choice(
                        answer = it.answer,
                        isCorrectChoice = it.correct,
                    )
                },
        )
    }
