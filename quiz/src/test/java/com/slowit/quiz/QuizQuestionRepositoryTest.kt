package com.slowit.quiz

import com.slowit.quiz.data.remote.api.QuizQuestionApi
import com.slowit.quiz.data.remote.model.ChoiceResponse
import com.slowit.quiz.data.remote.model.QuestionResponse
import com.slowit.quiz.data.remote.model.QuizResponse
import com.slowit.quiz.data.repositories.QuizQuestionRepositoryImpl
import com.slowit.quiz.domain.model.Choice
import com.slowit.quiz.domain.model.QuizQuestion
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class QuizQuestionRepositoryTest {

    private val mockQuizApiService: QuizQuestionApi = mockk()
    private val systemUnderTest = QuizQuestionRepositoryImpl(
        quizQuestionApi = mockQuizApiService
    )

    @Test
    fun `check if api service is making request when repository get quiz called`() = runTest {
        coEvery { mockQuizApiService.getQuizQuestions() } returns MOCKK_API_SERVICE_QUIZ_QUESTION_RESPONSE
        systemUnderTest.getQuizQuestions()
        coVerify(exactly = 1) { mockQuizApiService.getQuizQuestions() }
    }

    @Test
    fun `check if repository get questions is correctly map to domain model`() = runTest {
        coEvery { mockQuizApiService.getQuizQuestions() } returns MOCKK_API_SERVICE_QUIZ_QUESTION_RESPONSE
        val actual = systemUnderTest.getQuizQuestions()
        actual shouldBe MOCKK_DOMAIN_QUIZ_QUESTION
    }

    companion object {
        private val MOCKK_API_SERVICE_QUIZ_QUESTION_RESPONSE =
            QuizResponse(
                questions = listOf(
                    QuestionResponse(
                        question = "test1",
                        image = "test1",
                        time = 1,
                        choices = listOf(
                            ChoiceResponse(
                                correct = true,
                                answer = "test1"
                            )
                        ),
                        pointsMultiplier = 1
                    ),
                    QuestionResponse(
                        question = "test2",
                        image = "test2",
                        time = 2,
                        choices = listOf(
                            ChoiceResponse(
                                correct = true,
                                answer = "test2"
                            )
                        ),
                        pointsMultiplier = 1
                    ),
                    QuestionResponse(
                        question = "test3",
                        image = "test3",
                        time = 3,
                        choices = listOf(
                            ChoiceResponse(
                                correct = false,
                                answer = "test3"
                            )
                        ),
                        pointsMultiplier = 1
                    )
                )
            )


        private val MOCKK_DOMAIN_QUIZ_QUESTION = listOf(
            QuizQuestion(
                question = "test1",
                image = "test1",
                time = 1,
                choices = listOf(
                    Choice(
                        isCorrectChoice = true,
                        answer = "test1"
                    )
                )
            ), QuizQuestion(
                question = "test2",
                image = "test2",
                time = 2,
                choices = listOf(
                    Choice(
                        isCorrectChoice = true,
                        answer = "test2"
                    )
                )
            ), QuizQuestion(
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