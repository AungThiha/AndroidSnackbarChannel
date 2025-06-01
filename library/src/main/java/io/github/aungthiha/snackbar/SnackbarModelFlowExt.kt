package io.github.aungthiha.snackbar

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

@SuppressLint("ComposableNaming")
@Composable
fun Flow<SnackbarModel>.observeWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<SnackbarModel>,
) {
    LaunchedEffect(this) {
        flowWithLifecycle(lifecycle, minActiveState)
            .collect(collector)
    }
}

@Deprecated(
    message = "Renamed to observeWithLifecycle for better Compose conventions",
    replaceWith = ReplaceWith(
        "observeWithLifecycle(collector)"
    ),
    level = DeprecationLevel.WARNING
)
@SuppressLint("ComposableNaming")
@Composable
fun Flow<SnackbarModel>.collectWithLifecycle(
    lifecycle: Lifecycle = LocalLifecycleOwner.current.lifecycle,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: FlowCollector<SnackbarModel>,
) {
    LaunchedEffect(this) {
        flowWithLifecycle(lifecycle, minActiveState)
            .collect(collector)
    }
}
