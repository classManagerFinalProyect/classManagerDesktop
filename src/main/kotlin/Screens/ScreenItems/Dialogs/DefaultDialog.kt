package Screens.ScreenItems.Dialogs

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

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