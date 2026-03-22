package com.raja.kotlinpractice.data.remote.model

data class LoginRequest(
    val email: String,
    val password: String,
)

data class RegistrationRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val phoneNumber: String? = null,
)

data class ResetPasswordRequest(
    val email: String,
)

data class GuestAccessTokenRequest(
    val deviceId: String,
    val platform: String,
)

data class AuthTokenResponse(
    val accessToken: String,
    val refreshToken: String? = null,
    val tokenType: String = "Bearer",
    val expiresIn: Long? = null,
)

data class AuthMessageResponse(
    val message: String,
)
