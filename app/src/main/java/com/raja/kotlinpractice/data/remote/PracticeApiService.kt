package com.raja.kotlinpractice.data.remote

import retrofit2.http.GET

interface PracticeApiService {
    @GET("/")
    suspend fun ping(): Unit
}
