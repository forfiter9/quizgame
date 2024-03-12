package com.slowit.quiz.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuizResponse (
    @SerializedName("questions")
    val questions: List<QuestionResponse>
)