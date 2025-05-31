package io.github.aungthiha.snackbar

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

/**
 * This pattern with [rememberSnackbarHostController] and [SnackbarHostController] is inspired by
 * the official `NavController` implementation and a video from the Android Developers channel:
 * https://www.youtube.com/watch?v=rmv2ug-wW4U
 *
 * It provides a familiar and lifecycle-safe way for developers to work with snackbars.
 */
@Composable
fun rememberSnackbarHostController(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): SnackbarHostController {
    val context = LocalContext.current
    return remember(snackbarHostState) {
        SnackbarHostController(
            context = context,
            snackbarHostState = snackbarHostState
        )
    }
}

class SnackbarHostController(
    private val context: Context,
    val snackbarHostState: SnackbarHostState,
) {
    suspend fun showSnackbar(snackbarModel: SnackbarModel) {
        val result = snackbarHostState.showSnackbar(
            snackbarModel.message.unpackString(),
            snackbarModel.actionLabel?.unpackString(),
            snackbarModel.withDismissAction,
            snackbarModel.duration,
        )
        when (result) {
            SnackbarResult.Dismissed -> snackbarModel.onDismiss()
            SnackbarResult.ActionPerformed -> snackbarModel.onActionPerform()
        }
    }

    private fun SnackbarString.unpackString(): String = when (this) {
        is SnackbarString.Literal -> value
        is SnackbarString.Resource -> context.getString(value)
    }
}
