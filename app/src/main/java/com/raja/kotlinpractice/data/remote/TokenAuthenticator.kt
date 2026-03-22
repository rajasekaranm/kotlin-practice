package com.raja.kotlinpractice.data.remote

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenAuthenticator @Inject constructor(
    private val authTokenProvider: AuthTokenProvider,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.request.header("Authorization") == null) {
            return null
        }

        val newToken = authTokenProvider.refreshAccessToken()

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
}
