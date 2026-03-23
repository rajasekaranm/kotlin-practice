package com.raja.kotlinpractice.data.remote

import com.raja.kotlinpractice.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CommonHeadersInterceptor @Inject constructor(
    private val authTokenProvider: AuthTokenProvider,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("X-App-Environment", BuildConfig.ENVIRONMENT)

        val path = originalRequest.url.encodedPath
        when {
            path.isGuestAuthEndpoint() -> {
                requestBuilder.header("Authorization", "Bearer ${authTokenProvider.getGuestToken()}")
            }

            path.isUnauthenticatedEndpoint() -> {
                requestBuilder.removeHeader("Authorization")
            }

            else -> {
                requestBuilder.header("Authorization", "Bearer ${authTokenProvider.getAccessToken()}")
            }
        }

        return chain.proceed(requestBuilder.build())
    }
}
