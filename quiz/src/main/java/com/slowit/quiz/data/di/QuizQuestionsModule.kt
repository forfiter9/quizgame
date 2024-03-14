package com.slowit.quiz.data.di

import com.slowit.quiz.data.remote.api.QuizQuestionApi
import com.slowit.quiz.data.repositories.QuizQuestionRepositoryImpl
import com.slowit.quiz.domain.repository.QuizQuestionsRepository
import com.slowit.quiz.domain.usecase.GetQuizQuestionsUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object QuizQuestionsModule {
    @Provides
    @Singleton
    fun provideRocketApi(retrofit: Retrofit): QuizQuestionApi {
        return retrofit.create(QuizQuestionApi::class.java)
    }

    @Provides
    fun provideGetQuizQuestionsUseCase(quizQuestionsRepository: QuizQuestionsRepository): GetQuizQuestionsUseCase {
        return GetQuizQuestionsUseCase(quizQuestionsRepository)
    }

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {
        @Binds
        @Singleton
        fun bindQuizQuestionsRepository(impl: QuizQuestionRepositoryImpl): QuizQuestionsRepository
    }
}
