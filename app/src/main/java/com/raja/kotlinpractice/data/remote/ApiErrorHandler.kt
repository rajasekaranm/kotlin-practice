package com.raja.kotlinpractice.data.remote

import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiErrorHandler @Inject constructor() {
    suspend fun <T> safeApiCall(block: suspend () -> T): ApiResult<T> {
        return try {
            ApiResult.Success(block())
        } catch (throwable: Throwable) {
            when (throwable) {
                is HttpException -> ApiResult.Error(
                    message = throwable.message().orEmpty().ifBlank { "Request failed" },
                    code = throwable.code(),
                )

                is IOException -> ApiResult.Error("Network error. Please check your connection.")
                else -> ApiResult.Error(throwable.message.orEmpty().ifBlank { "Unexpected error occurred" })
            }
        }
    }
}
