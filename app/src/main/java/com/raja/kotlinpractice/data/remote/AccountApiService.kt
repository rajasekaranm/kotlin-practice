package com.raja.kotlinpractice.data.remote

import com.raja.kotlinpractice.data.remote.model.AccountResponse
import com.raja.kotlinpractice.data.remote.model.AuthMessageResponse
import com.raja.kotlinpractice.data.remote.model.ChangePasswordRequest
import com.raja.kotlinpractice.data.remote.model.UpdateAccountRequest
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Part

interface AccountApiService {
    @GET("account")
    suspend fun getAccount(): AccountResponse

    @PUT("account/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): AuthMessageResponse

    @DELETE("account")
    suspend fun deleteAccount(): AuthMessageResponse

    @PUT("account")
    suspend fun updateAccount(@Body request: UpdateAccountRequest): AccountResponse

    @Multipart
    @PUT("account/profile-picture")
    suspend fun uploadProfilePicture(
        @Part profilePicture: MultipartBody.Part,
    ): AccountResponse
}
