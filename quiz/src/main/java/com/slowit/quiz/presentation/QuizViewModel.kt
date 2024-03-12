package com.slowit.quiz.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.slowit.quiz.domain.usecase.GetQuizQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase
): ViewModel() {
    fun getQuestions() {
        viewModelScope.launch {
            val questions = getQuizQuestionsUseCase.invoke()
            Log.e("TEST@", "$questions", )
        }
    }
}