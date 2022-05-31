package Screens.Course.Components.MainBody

import Screens.Course.Components.MainBody.Classes.addNewClass
import Screens.Course.Components.MainBody.Events.Components.seeEvent
import Screens.Course.Components.MainBody.Events.createEvent
import Screens.Course.Components.MainBody.Events.modifierEvent
import Screens.Course.ViewModelCourse
import Screens.ScreenItems.Dialogs.defaultDialog
import Screens.ScreenItems.Dialogs.infoDialog
import Screens.ScreenItems.bigRectangleCard
import Screens.ScreenItems.bigVerticalCard
import Utils.LazyGridFor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import data.remote.Class
import data.remote.Event

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun events() {

    var reload by remember { mutableStateOf(false) }
    var modifierEvent by remember { mutableStateOf(false) }
    var sizeDropMenu by remember { mutableStateOf(IntSize.Zero) }
    var expanded by remember { mutableStateOf(false) }
    var showToast = remember { mutableStateOf(false) }
    var toastTitle by remember{ mutableStateOf("Title") }
    var toastText by remember{ mutableStateOf("Text") }
    var selectedEvent by remember { mutableStateOf(Event("","","","","","","")) }


    if(showToast.value) {
        infoDialog(
            showToast = showToast,
            title = toastTitle,
            text = toastText
        )
    }


    if (modifierEvent) {
        defaultDialog(
            resizable = false,
            title = "Information Event",
            onClose = { modifierEvent = it },
            state = rememberDialogState(
                position = WindowPosition(Alignment.Center),
                size = DpSize(550.dp, 600.dp)
            ),
            content = {
                if(ViewModelCourse.currentUser.rol == "admin" || ViewModelCourse.currentUser.rol == "profesor") {
                    modifierEvent(
                        event = selectedEvent,
                        onDeleteEvent = {
                            modifierEvent = false
                            reload = true
                            showToast.value = true
                            toastTitle = "Evento eliminado"
                            toastText = "Se ha eliminado el evento correctamente"
                        },
                        onModifierEvent = {
                            modifierEvent = false
                            showToast.value = true
                            toastTitle = "Evento actualizado"
                            toastText = "Se ha actualizado el evento correctamente"
                        }
                    )
                }
                else {
                    seeEvent(
                        event = selectedEvent,
                        onCloseRequest = { modifierEvent = false },
                    )
                }

            }
        )
    }

    LaunchedEffect(reload) {
        if(reload) reload = false
    }



    Row(
        content = {
            Column(
                modifier = Modifier.fillMaxHeight().fillMaxWidth(0.25f),
                content = {
                    Text(
                        text = "Opciones de los eventos",
                    )
                    if(ViewModelCourse.currentUser.rol == "admin" || ViewModelCourse.currentUser.rol == "profesor") {
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
                                            onCreateEvent = {
                                                expanded = false
                                                reload = true
                                                showToast.value = true
                                                toastTitle = "Evento creado"
                                                toastText = "El evento se ha creado con éxito"
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                }
            )

            Column(
                content = {
                    if (!reload) {

                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(200.dp),
                            content = {
                                this.itemsIndexed(ViewModelCourse.currentEvents) { index: Int, item: Event ->
                                    bigVerticalCard(
                                        event = item,
                                        onClick = {
                                            selectedEvent = item
                                            modifierEvent = true
                                        }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )


}