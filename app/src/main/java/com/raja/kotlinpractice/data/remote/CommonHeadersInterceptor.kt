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
        val request = chain.request().newBuilder()
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .header("X-App-Environment", BuildConfig.ENVIRONMENT)
            .header("Authorization", "Bearer ${authTokenProvider.getAccessToken()}")
            .build()

        return chain.proceed(request)
    }
}
