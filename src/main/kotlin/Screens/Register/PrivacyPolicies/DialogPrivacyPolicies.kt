package Screens.Register.PrivacyPolicies

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState

@Composable
fun PrivacyPoliciesDialog(
    onClose: (Boolean) -> Unit
) {

    Dialog(
        onCloseRequest = { onClose(false) },
        title = "Privacy Policies",
        resizable = false,
        state = rememberDialogState(
            position = WindowPosition(Alignment.Center),
            size = DpSize(800.dp, 600.dp)
        ),
        content = {
            MainPrivacyPolicies()
        }
    )

}