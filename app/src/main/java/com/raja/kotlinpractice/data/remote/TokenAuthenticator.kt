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
        val path = response.request.url.encodedPath
        val authHeader = response.request.header("Authorization") ?: return null

        if (path.isGuestAuthEndpoint() || path.isUnauthenticatedEndpoint()) {
            return null
        }

        if (!authHeader.startsWith("Bearer ")) {
            return null
        }

        val newToken = authTokenProvider.refreshAccessToken()

        return response.request.newBuilder()
            .header("Authorization", "Bearer $newToken")
            .build()
    }
}
