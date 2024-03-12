package com.slowit.quiz.data.remote.model

import com.google.gson.annotations.SerializedName

data class ChoiceResponse(
    @SerializedName("answer")
    val answer: String,
    @SerializedName("correct")
    val correct: Boolean
)
