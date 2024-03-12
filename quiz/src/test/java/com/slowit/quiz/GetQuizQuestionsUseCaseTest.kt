package com.slowit.quiz

import com.slowit.quiz.domain.model.Choice
import com.slowit.quiz.domain.model.QuizQuestion
import com.slowit.quiz.domain.repository.QuizQuestionsRepository
import com.slowit.quiz.domain.usecase.GetQuizQuestionsUseCase
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetQuizQuestionsUseCaseTest {

    private val mockQuizQuestionsRepository: QuizQuestionsRepository = mockk()
    private val systemUnderTest = GetQuizQuestionsUseCase(
        quizQuestionsRepository = mockQuizQuestionsRepository
    )

    @Test
    fun `check if rest post are returned in use case`() = runTest {
        coEvery { mockQuizQuestionsRepository.getQuizQuestions() } returns MOCKK_QUIZ_QUESTION
        val actual = systemUnderTest.invoke()
        actual shouldBe MOCKK_QUIZ_QUESTION
    }

    @Test
    fun `check if repository function is called when use case invoked`() = runTest {
        coEvery { mockQuizQuestionsRepository.getQuizQuestions() } returns MOCKK_QUIZ_QUESTION
        systemUnderTest.invoke()
        coVerify(exactly = 1) { mockQuizQuestionsRepository.getQuizQuestions() }
    }

    companion object {
        private val MOCKK_QUIZ_QUESTION = listOf(
            QuizQuestion (
                question = "test1",
                image = "test1",
                time = 1,
                choices = listOf(
                    Choice(
                        isCorrectChoice = true,
                        answer = "test1"
                    )
                )
            ), QuizQuestion (
                question = "test2",
                image = "test2",
                time = 2,
                choices = listOf(
                    Choice(
                        isCorrectChoice = true,
                        answer = "test2"
                    )
                )
            ), QuizQuestion (
                question = "test3",
                image = "test3",
                time = 3,
                choices = listOf(
                    Choice(
                        isCorrectChoice = false,
                        answer = "test3"
                    )
                )
            )
        )
    }
}