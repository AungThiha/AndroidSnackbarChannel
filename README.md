# AndroidSnackbarChannel

A lightweight, lifecycle-safe snackbar event dispatcher for Jetpack Compose that addresses common pitfalls of using SharedFlow and StateFlow.

> ⚠️ **Using Compose Multiplatform?**  
> This library has a separate implementation for Compose Multiplatform. See [SnackbarChannel for Compose Multiplatform](https://github.com/AungThiha/SnackbarChannel).
>
> This version is tailored for pure Android and does not rely on `getString` or `StringResource` from `org.jetbrains.compose.resources`.
## Why use AndroidSnackbarChannel?

`AndroidSnackbarChannel` addresses the common pitfalls of using `StateFlow`, `SharedFlow`, or even `StateFlow<List<Event>>`.

- `StateFlow` re-emits on config changes, leading to duplicate snackbars.
- `SharedFlow(replay = 0)` may drop events when no collector is active.
- `SharedFlow(replay = 1)` can treat each lifecycle change as a new subscription, re-emitting events.
- List-based workarounds (e.g., `StateFlow<List<String>>`) require manual state updates to remove consumed items, adding extra complexity and visual noise to the `ViewModel`.

`AndroidSnackbarChannel` avoids these issues:

- Emits events reliably even across lifecycle changes.
- No risk of duplicates or dropped events.
- No manual list mutation or bookkeeping required.

It’s a focused solution that keeps your snackbar logic clean, lifecycle-aware, and easy to use.

---

## Features

- One-liner API for triggering snackbars from your `ViewModel`
- No more missed or duplicated snackbars
- Lifecycle-aware: events are only collected when the UI is active
- No brittle base classes - favors composition over inheritance using Kotlin delegation
- Converts string resources automatically (e.g. R.string.key → "Actual String")
- Supports all original showSnackbar parameters (action labels, duration, callbacks, etc.)
- Fully unit-testable

---

## Installation

```kotlin
dependencies {
    implementation("io.github.aungthiha-android-snackbar-channel-1.0.2")
}
```

---

## Setup

### 1. Add `SnackbarChannel` to your `ViewModel`
By default, SnackbarChannel uses `Channel.UNLIMITED` and `BufferOverflow.SUSPEND` to ensure no snackbar events are dropped.
```kotlin
import io.github.aungthiha.snackbar.SnackbarChannel
import io.github.aungthiha.snackbar.SnackbarChannelOwner

class MyViewModel(
    private val snackbarChannel: SnackbarChannel = SnackbarChannel() // Default: Channel.UNLIMITED
) : ViewModel(), SnackbarChannelOwner by snackbarChannel {

    fun showSimpleSnackbar() {
        showSnackBar(message = R.string.hello_world)
    }
}
```
While the defaults are recommended for most use cases, both the channel capacity and `onBufferOverflow` strategy are configurable:
```kotlin
SnackbarChannel(
    capacity = Channel.RENDEZVOUS, // Or Channel.BUFFERED, etc.
    onBufferOverflow = BufferOverflow.DROP_OLDEST, // Or BufferOverflow.DROP_LATEST
) 
```

### 2. Observe snackbars in your Composable
```kotlin
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import io.github.aungthiha.snackbar.collectWithLifecycle
import io.github.aungthiha.snackbar.rememberSnackbarHostController

@Composable
fun MyScreen(viewModel: MyViewModel = viewModel()) {
    
    val snackbarHostController = rememberSnackbarHostController()
    viewModel.snackbarFlow.collectWithLifecycle {
        snackbarHostController.showSnackbar(it)
    }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostController.snackbarHostState) }) {
        Button(onClick = { viewModel.showSimpleSnackbar() }) {
            Text("Show Snackbar")
        }
    }
}
```

---

## API Overview
Use showSnackBar(...) from your ViewModel. You can pass string resources, string literals, or even mix both using SnackbarString.
```kotlin
// All parameters
showSnackBar(
    message = R.string.hello_world, // can be either string resource, String or SnackbarString
    actionLabel = SnackbarString("ok"), // can be either string resource, String or SnackbarString
    withDismissAction = true,
    duration = SnackbarDuration.Indefinite,
    onActionPerform = { /* handle action */ },
    onDismiss = { /* handle dismiss */ }
)

// Using a string resource
showSnackBar(
    message = R.string.hello_world,
    actionLabel = R.string.ok
)

// Using a raw string (e.g., from backend or dynamic input)
showSnackBar(
    message = "Something went wrong!",
    actionLabel = "Retry"
)

// Mixing string types
showSnackBar(
    message = "မင်္ဂလာပါ",
    actionLabel = R.string.ok
)

showSnackBar(
    message = R.string.hello_world,
    actionLabel = "ok"
)
```
All parameters are optional except the message.   
For more example usages, see [AppViewModel.kt](./app/src/main/java/io/github/aungthiha/snackbar/demo/MainViewModel.kt)

---

## Unit Testing
You can test snackbar emissions using `runTest` and collecting from `snackbarFlow`.

```kotlin
class MyViewModelTest {

    private val viewModel = MyViewModel()

    @Test
    fun snackbar_is_emitted() = runTest {
        viewModel.showSimpleSnackbar()

        val snackbarModel = viewModel.snackbarFlow.first()

        assertEquals(
            SnackbarString(R.string.hello_world),
            snackbarModel.message
        )
    }
}
```
---

## Contributing
PRs and feedback welcome!

---

## License
MIT
