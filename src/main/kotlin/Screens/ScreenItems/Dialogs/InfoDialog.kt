package Screens.ScreenItems.Dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun infoDialog(
    showToast: MutableState<Boolean>,
    title: String,
    text: String
) {
    AlertDialog(
        modifier = Modifier.fillMaxWidth(0.2f),
        onDismissRequest = {
            showToast.value = false
       },
        title = { Text(text = title, modifier = Modifier.fillMaxWidth()) },
        text = { Text(text = text, modifier = Modifier.fillMaxWidth()) },
        buttons = {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth(),
                content = {
                    TextButton(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        onClick = {
                            showToast.value = false
                        },
                        content = {
                            Text(text = "Aceptar")
                        }
                    )
                }
            )
        }
    )
}

