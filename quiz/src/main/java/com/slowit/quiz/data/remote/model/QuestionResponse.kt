package com.slowit.quiz.data.remote.model

import com.google.gson.annotations.SerializedName

data class QuestionResponse(
    @SerializedName("question")
    val question: String,
    @SerializedName("time")
    val time: Long,
    @SerializedName("image")
    val image: String,
    @SerializedName("choices")
    val choices: List<ChoiceResponse>,
    @SerializedName("pointsMultiplier")
    val pointsMultiplier: Int
)
