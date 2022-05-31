package Screens.ScreenItems.Dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun confirmAlertDialog(
    title: String,
    subtitle: String,
    onValueChangeGoBack: () -> Unit,
    onClickAccept: () -> Unit
) {

    Column(
        content = {
            AlertDialog(
                modifier = Modifier
                    .width(400.dp)
                    .height(200.dp),
                onDismissRequest = {
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(subtitle)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onValueChangeGoBack()
                            onClickAccept()
                        },
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onValueChangeGoBack()
                        },
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    )
}