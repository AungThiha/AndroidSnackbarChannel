package io.github.aungthiha.snackbar.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.aungthiha.snackbar.demo.ui.theme.DemoTheme
import io.github.aungthiha.snackbar.observeWithLifecycle
import io.github.aungthiha.snackbar.rememberSnackbarHostController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel { MainViewModel() }
) {

    val snackbarHostController = rememberSnackbarHostController()
    viewModel.snackbarFlow.observeWithLifecycle {
        snackbarHostController.showSnackbar(it)
    }

    val onActionPerformCalled by viewModel.onActionPerformCalled.collectAsStateWithLifecycle()
    val onDismissCalled by viewModel.onDismissedCalled.collectAsStateWithLifecycle()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostController.snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text("AndroidSnackbarChannel")

            Spacer(modifier = Modifier.height(16.dp))

            Text("Number of times onActionPerform called: $onActionPerformCalled")
            Text("Number of times onDismiss called: $onDismissCalled")

            Button(
                onClick = { viewModel.snackbarWithStringResource() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Snackbar with StringResource")
            }

            Button(
                onClick = { viewModel.snackbarWithStringLiteral() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Snackbar with String Literal")
            }

            Button(
                onClick = { viewModel.snackbarWithMixedStringTypes() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Snackbar with mixed String types")
            }

            Button(
                onClick = { viewModel.snackbarWithAction() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Show Snackbar with action")
            }

            Button(
                onClick = { viewModel.snackbarWithDismissAction() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Show Snackbar with dismiss action")
            }

            Button(
                onClick = { viewModel.snackbarWithOnActionPerformCallback() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Show Snackbar with onActionPerform callback")
            }

            Button(
                onClick = { viewModel.snackbarWithOnDismissCallback() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Show Snackbar with onDismiss callback")
            }

            Button(
                onClick = { viewModel.indefiniteSnackbar() },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Indefinite Snackbar")
            }

            Spacer(modifier = Modifier.weight(1f)) // Push buttons up but keep them below content
        }
    }
}
