package Screens.ScreenComponents.TopAppBar.Account

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun dialogAccount(
    onClose: (Boolean) -> Unit,
    onCloseSession: () -> Unit
) {
    Dialog(
        onCloseRequest = { onClose(false) },
        title = "Mi Account",
        resizable = true,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(900.dp, 700.dp)
        ),
        content = {
            MainAccount(
                onCloseSession = { onCloseSession() }
            )
        }
    )
}