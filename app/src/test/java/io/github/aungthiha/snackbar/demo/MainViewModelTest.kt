package io.github.aungthiha.snackbar.demo

import io.github.aungthiha.snackbar.SnackbarString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.assertEquals

class MainViewModelTest {
    val viewModel = MainViewModel()
    @Test
    fun snackbarEmitted() = runTest {
        viewModel.snackbarWithStringResource()

        val snackbarModel = viewModel.snackbarFlow.first()

        assertEquals(
            SnackbarString(R.string.hello_from_AndroidSnackbarChannel),
            snackbarModel.message
        )
    }
}
