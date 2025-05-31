package io.github.aungthiha.snackbar

import androidx.annotation.StringRes

sealed interface SnackbarString {
    @JvmInline
    value class Literal(val value: String) : SnackbarString

    @JvmInline
    value class Resource(@StringRes val value: Int) : SnackbarString
}

fun SnackbarString(value: String) = SnackbarString.Literal(value)

fun SnackbarString(@StringRes value: Int) = SnackbarString.Resource(value)
