package io.github.aungthiha.snackbar

import androidx.annotation.StringRes

sealed interface SnackbarString {
    @JvmInline
    value class Literal(val value: String) : SnackbarString

    @JvmInline
    value class Resource(@StringRes val value: Int) : SnackbarString
}

/**
 * Creates a [SnackbarString.Literal] from a raw [String].
 *
 * Allows developers to instantiate either a [Literal] or [Resource] using the same `SnackbarString(...)` function name,
 * improving discoverability and reducing cognitive overhead.
 */
fun SnackbarString(value: String) = SnackbarString.Literal(value)

/**
 * Creates a [SnackbarString.Resource] from a string resource ID.
 *
 * Allows developers to instantiate either a [Literal] or [Resource] using the same `SnackbarString(...)` function name,
 * improving discoverability and reducing cognitive overhead.
 */
fun SnackbarString(@StringRes value: Int) = SnackbarString.Resource(value)
