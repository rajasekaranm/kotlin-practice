package com.raja.kotlinpractice.data.remote

import com.raja.kotlinpractice.data.remote.model.AuthMessageResponse
import com.raja.kotlinpractice.data.remote.model.AuthTokenResponse
import com.raja.kotlinpractice.data.remote.model.GuestAccessTokenRequest
import com.raja.kotlinpractice.data.remote.model.LoginRequest
import com.raja.kotlinpractice.data.remote.model.RegistrationRequest
import com.raja.kotlinpractice.data.remote.model.ResetPasswordRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthTokenResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegistrationRequest): AuthMessageResponse

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): AuthMessageResponse

    @POST("auth/guest-token")
    suspend fun guestAccessToken(@Body request: GuestAccessTokenRequest): AuthTokenResponse
}
