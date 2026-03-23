package com.raja.kotlinpractice.extensions

import android.util.Patterns

fun String.isValidEmail(): Boolean = isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword(minLength: Int = 6): Boolean = trim().length >= minLength

fun String.isValidName(minLength: Int = 2): Boolean = trim().length >= minLength
