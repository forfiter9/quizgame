package com.slowit.quiz.domain.model

data class QuizQuestion(
    val question: String,
    val time: Int,
    val image: String,
    val choices: List<Choice>
)
