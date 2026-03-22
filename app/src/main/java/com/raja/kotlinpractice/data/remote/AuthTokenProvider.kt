package com.raja.kotlinpractice.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenProvider @Inject constructor() {
    private var accessToken: String = "demo-access-token"
    private var refreshToken: String = "demo-refresh-token"

    fun getAccessToken(): String = accessToken

    fun refreshAccessToken(): String {
        accessToken = "$refreshToken-refreshed"
        return accessToken
    }
}
