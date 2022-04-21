package Screens.ScreenComponents.TopAppBar.items

import Screens.Register.PrivacyPolicies.MainPrivacyPolicies
import Screens.ScreenComponents.TopAppBar.Profile.mainProfile
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun dialogProfile(
    onClose: (Boolean) -> Unit
) {
    Dialog(
        onCloseRequest = { onClose(false) },
        title = "Mi Profile",
        resizable = true,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(900.dp, 700.dp)
        ),
        content = {
            mainProfile()
        }
    )
}