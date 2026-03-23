package com.raja.kotlinpractice.data.repository

import com.raja.kotlinpractice.data.remote.AccountApiService
import com.raja.kotlinpractice.data.remote.ApiErrorHandler
import com.raja.kotlinpractice.data.remote.ApiResult
import com.raja.kotlinpractice.data.remote.model.AccountResponse
import com.raja.kotlinpractice.data.remote.model.AuthMessageResponse
import com.raja.kotlinpractice.data.remote.model.ChangePasswordRequest
import com.raja.kotlinpractice.data.remote.model.UpdateAccountRequest
import okhttp3.MultipartBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AccountRepository @Inject constructor(
    private val accountApiService: AccountApiService,
    private val apiErrorHandler: ApiErrorHandler,
) {
    suspend fun getAccount(): ApiResult<AccountResponse> =
        apiErrorHandler.safeApiCall { accountApiService.getAccount() }

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): ApiResult<AuthMessageResponse> =
        apiErrorHandler.safeApiCall {
            accountApiService.changePassword(
                ChangePasswordRequest(
                    currentPassword = currentPassword,
                    newPassword = newPassword,
                )
            )
        }

    suspend fun deleteAccount(): ApiResult<AuthMessageResponse> =
        apiErrorHandler.safeApiCall { accountApiService.deleteAccount() }

    suspend fun updateAccount(
        fullName: String,
        phoneNumber: String? = null,
        bio: String? = null,
    ): ApiResult<AccountResponse> =
        apiErrorHandler.safeApiCall {
            accountApiService.updateAccount(
                UpdateAccountRequest(
                    fullName = fullName,
                    phoneNumber = phoneNumber,
                    bio = bio,
                )
            )
        }

    suspend fun uploadProfilePicture(profilePicture: MultipartBody.Part): ApiResult<AccountResponse> =
        apiErrorHandler.safeApiCall {
            accountApiService.uploadProfilePicture(profilePicture)
        }
}
