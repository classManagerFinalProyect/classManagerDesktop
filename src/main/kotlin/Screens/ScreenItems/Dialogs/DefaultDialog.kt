package Screens.ScreenItems.Dialogs

import Screens.ScreenComponents.TopAppBar.Profile.mainProfile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun defaultDialog(
    onClose: (Boolean) -> Unit,
    content: @Composable () -> Unit,
    state: DialogState,
    title: String,
    resizable: Boolean
){
    Dialog(
        onCloseRequest = { onClose(false) },
        title = title,
        resizable = resizable,
        state = state,
        content = { content() }
    )
}