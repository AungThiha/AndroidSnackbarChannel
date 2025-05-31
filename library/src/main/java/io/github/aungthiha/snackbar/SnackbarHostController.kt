package io.github.aungthiha.snackbar

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

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
