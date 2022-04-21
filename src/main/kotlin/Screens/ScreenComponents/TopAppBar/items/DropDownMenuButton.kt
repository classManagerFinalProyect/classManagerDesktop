package Screens.MainAppScreen.Items

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize

@Composable
fun dropDownMenuButton(
    textOfButton: String
) {

    var expanded by remember { mutableStateOf(false) }
    var size by remember { mutableStateOf(Size.Zero) }

    Column(
        content = {
            Button(
                modifier = Modifier
                    .onGloballyPositioned { coordinates ->
                        size = coordinates.size.toSize()
                    },
                onClick = {
                    expanded = !expanded
                },
                content = {
                    Text(
                        text = "${textOfButton}",
                        fontSize = 14.sp
                    )
                }
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },

                content = {
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                        },
                        content = {
                            Text(text = "Nuevo curso")
                        }
                    )
                    DropdownMenuItem(
                        onClick = {
                            expanded = false
                        },
                        content = {
                            Text(text = "Nueva clase")
                        }
                    )
                }
            )
        }
    )
}