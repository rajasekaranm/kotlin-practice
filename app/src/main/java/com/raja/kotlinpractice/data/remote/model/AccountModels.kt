package com.raja.kotlinpractice.data.remote.model

data class AccountResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String? = null,
    val profileImageUrl: String? = null,
)

data class ChangePasswordRequest(
    val currentPassword: String,
    val newPassword: String,
)

data class UpdateAccountRequest(
    val fullName: String,
    val phoneNumber: String? = null,
    val bio: String? = null,
)
