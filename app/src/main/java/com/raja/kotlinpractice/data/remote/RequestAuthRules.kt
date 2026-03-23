package com.raja.kotlinpractice.data.remote

internal fun String.isGuestAuthEndpoint(): Boolean =
    this == "/auth/login" ||
        this == "/auth/register" ||
        this == "/auth/reset-password"

internal fun String.isUnauthenticatedEndpoint(): Boolean =
    this == "/auth/guest-token"
