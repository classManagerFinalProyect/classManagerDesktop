package Screens.Course.Components.MainBody.Events

import Screens.ScreenComponents.TopAppBar.Profile.mainProfile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.remote.Event

@Composable
fun dialogModifierEvent(
    onClose: (Boolean) -> Unit,
    event: Event
) {

    Dialog(
        onCloseRequest = { onClose(false) },
        title = "Modifier Event",
        resizable = true,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(550.dp, 600.dp)
        ),
        content = {
            modifierEvent(
                event = event,
                onCloseRequest = { onClose(false) }
            )
        }
    )
}