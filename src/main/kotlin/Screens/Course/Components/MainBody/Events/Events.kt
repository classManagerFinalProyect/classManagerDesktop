package Screens.Course.Components.MainBody

import Screens.Course.Components.MainBody.Classes.addNewClass
import Screens.Course.Components.MainBody.Events.createEvent
import Screens.Course.Components.MainBody.Events.modifierEvent
import Screens.Course.ViewModelCourse
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.bigVerticalCard
import Utils.LazyGridFor
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberDialogState
import data.remote.Event

@Composable
fun events() {

    var reload by remember { mutableStateOf(false) }
    var modifierEvent by remember { mutableStateOf(false) }
    var sizeDropMenu by remember { mutableStateOf(IntSize.Zero) }
    var expanded by remember { mutableStateOf(false) }

    var selectedEvent by remember { mutableStateOf(Event("","","","","","","")) }
    if (modifierEvent) {
        defaultDialog(
            resizable = true,
            title = "Modifier Event",
            onClose = { modifierEvent = it },
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(550.dp, 600.dp)
            ),
            content = {
                modifierEvent(
                    event = selectedEvent,
                    onCloseRequest = { modifierEvent = false }
                )
            }
        )
    }

    LaunchedEffect(reload) {
        if(reload) {
            reload = false
        }
    }

    Row(
        content = {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.25f),
                content = {
                    Text(
                        text = "Opciones de eventos",
                    )
                    TextButton(
                        modifier = Modifier
                            .onGloballyPositioned { coordinates ->
                                sizeDropMenu = coordinates.size
                            },
                        onClick = {
                            expanded = !expanded
                        },
                        content = {
                            Text( text = "Añadir un nuevo evento")

                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                content = {
                                   createEvent(
                                       onCloseRequest = {
                                           expanded = false
                                           reload = true
                                       }
                                   )
                                }
                            )
                        }
                    )
                }
            )

            Column(
                content = {
                    if (!reload) {
                        LazyGridFor(
                            items = ViewModelCourse.currentEvents,
                            rowSize = 5,
                            itemContent = {
                                bigVerticalCard(
                                    event = it,
                                    onClick = {
                                        selectedEvent = it
                                        modifierEvent = true
                                    }
                                )
                            }
                        )
                    }
                }
            )
        }
    )


}