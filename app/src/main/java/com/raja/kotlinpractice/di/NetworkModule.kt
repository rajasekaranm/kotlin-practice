package com.raja.kotlinpractice.di

import com.raja.kotlinpractice.data.remote.PracticeApiService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object NetworkModule {
    @Provides
    @Singleton
    fun providePracticeApiService(retrofit: Retrofit): PracticeApiService =
        retrofit.create(PracticeApiService::class.java)
}
