package io.github.aungthiha.snackbar.demo

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val snackbarChannel: SnackbarChannel = SnackbarChannel()
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    private val mutableOnActionPerformCalled: MutableStateFlow<Int> = MutableStateFlow(0)
    val onActionPerformCalled: StateFlow<Int> = mutableOnActionPerformCalled

    private val mutableOnDismissedCalled: MutableStateFlow<Int> = MutableStateFlow(0)
    val onDismissedCalled: StateFlow<Int> = mutableOnDismissedCalled

    fun snackbarWithStringResource() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel
        )
    }

    fun snackbarWithStringLiteral() {
        showSnackBar(
            message = "hey"
        )
    }

    fun snackbarWithMixedStringTypes() {
        showSnackBar(
            message = "မင်္ဂလာပါ",
            actionLabel = R.string.ok,
        )
    }

    fun snackbarWithAction() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel,
            actionLabel = R.string.ok
        )
    }

    fun snackbarWithDismissAction() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel,
            withDismissAction = true
        )
    }

    fun snackbarWithOnActionPerformCallback() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel,
            actionLabel = R.string.ok,
            onActionPerform = {
                mutableOnActionPerformCalled.update { it + 1 }
            }
        )
    }

    fun snackbarWithOnDismissCallback() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel,
            onDismiss = {
                mutableOnDismissedCalled.update { it + 1 }
            }
        )
    }

    fun indefiniteSnackbar() {
        showSnackBar(
            message = R.string.hello_from_AndroidSnackbarChannel,
            withDismissAction = true,
            duration = SnackbarDuration.Indefinite
        )
    }
}
