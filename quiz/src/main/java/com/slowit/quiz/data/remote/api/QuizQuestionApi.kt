package com.slowit.quiz.data.remote.api

import com.slowit.quiz.data.remote.model.QuizResponse
import retrofit2.http.GET

interface QuizQuestionApi {
    @GET("/rest/kahoots/fb4054fc-6a71-463e-88cd-243876715bc1")
    suspend fun getQuizQuestions(): QuizResponse
}